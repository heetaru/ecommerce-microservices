package org.example.catalogservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.catalogservice.dto.OrderPlacedEvent;
import org.example.catalogservice.entity.Product;
import org.example.catalogservice.repository.ProductRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {
    private final ProductRepository productRepository;

    @KafkaListener(topics = "order-events", groupId = "heetaru-group")
    @Transactional
    public void consumeOrderEvent(OrderPlacedEvent event) {
        log.info("Received new order event from Kafka: {}", event);

        Product product = productRepository.findById(event.productId())
                .orElseThrow(
                        () -> new RuntimeException("Cannot find product with id: " + event.productId())
                );
        if (product.getStockQuantity() < event.quantity()){
            log.warn("Cannot receive {} products. There are only {} products on the stock",
                    event.quantity(),
                    product.getStockQuantity());
            return;
        }
        product.setStockQuantity(product.getStockQuantity() - event.quantity());

        productRepository.save(product);
    }

}
