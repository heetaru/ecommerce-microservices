package org.example.catalogservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductRequestDto(
        @NotBlank
        String name,
        String description,
        @PositiveOrZero
        BigDecimal price,
        @PositiveOrZero
        Integer stockQuantity
) {
}
