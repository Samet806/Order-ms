package com.meta.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(Integer id,
                             @NotNull(message = "Product name is required")
                             String name,
                             @NotNull(message = "Product description is required")
                             String description,
                             @Positive(message = "Product uantity must be positive")
                             double availableQuantity,
                             BigDecimal price,
                             Integer categoryId) {
}
