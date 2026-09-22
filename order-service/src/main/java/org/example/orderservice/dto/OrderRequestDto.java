package org.example.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record OrderRequestDto(
        @NotNull
        Long productId,
        @NotNull
        BigDecimal quantity
) {
}
