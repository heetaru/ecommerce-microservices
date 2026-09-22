package org.example.orderservice.dto;

public record OrderPlacedEvent(
        Long productId, Integer quantity
) {}
