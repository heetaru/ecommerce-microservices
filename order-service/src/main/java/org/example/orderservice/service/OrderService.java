package org.example.orderservice.service;

import lombok.RequiredArgsConstructor;
import org.example.orderservice.controller.OrderController;
import org.example.orderservice.dto.OrderPlacedEvent;
import org.example.orderservice.dto.OrderRequestDto;
import org.example.orderservice.dto.OrderResponseDto;
import org.example.orderservice.entity.Order;
import org.example.orderservice.mapper.OrderMapper;
import org.example.orderservice.repository.OrderRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public List<OrderResponseDto> getAllOrders() {
        List<Order> allOrders = orderRepository.findAll();
        return allOrders.stream()
                .map(orderMapper::toDto)
                .toList();
    }

    public OrderResponseDto createOrder(OrderRequestDto orderRequestDto) {
        Order newOrder = orderMapper.toEntity(orderRequestDto);
        Order savedOrder = orderRepository.save(newOrder);

        OrderPlacedEvent event = new OrderPlacedEvent(
                savedOrder.getId(),
                savedOrder.getProductId(),
                savedOrder.getQuantity()
        );
        kafkaTemplate.send("order-events", event);

        return orderMapper.toDto(savedOrder);

    }
}
