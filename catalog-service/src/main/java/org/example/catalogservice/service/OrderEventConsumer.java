package org.example.catalogservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.catalogservice.dto.InventoryResponseEvent;
import org.example.catalogservice.dto.OrderPlacedEvent;
import org.example.catalogservice.entity.ProcessedMessage;
import org.example.catalogservice.entity.Product;
import org.example.catalogservice.repository.ProcessedMessageRepository;
import org.example.catalogservice.repository.ProductRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {
    private final ProductRepository productRepository;
    private final ProcessedMessageRepository processedMessageRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    @KafkaListener(topics = "order-events", groupId = "heetaru-group")
    @Transactional
    public void consumeOrderEvent(OrderPlacedEvent event) {
        log.info("Received new order event from Kafka: {}", event);

        if (processedMessageRepository.existsById(event.orderId())){
            log.warn("Order with id {} has been already completed", event.orderId());
            return;
        }

        Product product = productRepository.findById(event.productId())
                .orElseThrow(
                        () -> new RuntimeException("Cannot find product with id: " + event.productId())
                );

        if (product.getStockQuantity() < event.quantity()){

            log.warn("Cannot receive {} products. There are only {} products on the stock",
                    event.quantity(),
                    product.getStockQuantity());

            InventoryResponseEvent failedResponse = new InventoryResponseEvent(
                    event.orderId(),
                    "FAILED",
                    "THIS ORDER HAS BEEN ALREADY COMPLETED"
            );
            kafkaTemplate.send("inventory-event", failedResponse);
            return;
        }
        product.setStockQuantity(product.getStockQuantity() - event.quantity());
        productRepository.save(product);

        processedMessageRepository.save(new ProcessedMessage(event.orderId(), LocalDateTime.now()));

        InventoryResponseEvent succussedResponse = new InventoryResponseEvent(
                event.orderId(),
                "SUCCESS",
                "ORDER HAS BEEN COMPLETED"
        );
        kafkaTemplate.send("inventory-event", succussedResponse);

    }



}
