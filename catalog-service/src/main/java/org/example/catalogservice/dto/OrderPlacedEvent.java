package org.example.catalogservice.dto;

public record OrderPlacedEvent(
        Long orderId,
        Long productId,
        Integer quantity
) {
}