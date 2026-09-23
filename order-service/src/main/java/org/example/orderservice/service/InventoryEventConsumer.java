package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.orderservice.dto.InventoryResponseEvent;
import org.example.orderservice.entity.Order;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryEventConsumer {
    private final OrderRepository orderRepository;

    @KafkaListener(topics = "inventory-event", groupId = "order-group")
    public void consumeInventoryResponse(InventoryResponseEvent event){
        Order currentOrder = orderRepository.findById(event.orderId()).orElseThrow(() -> new RuntimeException("Order with id "+ event.orderId() +" does not exist"));
        if (event.status().equals("SUCCESS")){
            currentOrder.setStatus("SUCCESS");
        } else {
            currentOrder.setStatus("REJECTED");
        }
        orderRepository.save(currentOrder);
    }
}
