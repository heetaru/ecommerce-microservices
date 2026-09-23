package org.example.orderservice.dto;

public record OrderPlacedEvent(
        Long orderId,
        Long productId,
        Integer quantity
) {}
