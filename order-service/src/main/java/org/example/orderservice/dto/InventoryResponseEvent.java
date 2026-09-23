package org.example.orderservice.dto;

public record InventoryResponseEvent(
        Long orderId,
        String status,
        String reason
) {

}
