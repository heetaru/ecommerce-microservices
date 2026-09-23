package org.example.catalogservice.dto;

public record InventoryResponseEvent(
        Long orderId,
        String status,
        String reason
) {
}
