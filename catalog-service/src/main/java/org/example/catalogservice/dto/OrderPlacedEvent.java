package org.example.catalogservice.dto;

public record OrderPlacedEvent(
        Long productId,
        Integer quantity
) {
}