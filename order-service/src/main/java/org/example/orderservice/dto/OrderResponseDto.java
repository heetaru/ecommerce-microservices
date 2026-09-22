package org.example.orderservice.dto;

import java.math.BigDecimal;

public record OrderResponseDto(
        Long id,
        Long productId,
        BigDecimal quantity,
        String status
) {
}
