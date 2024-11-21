package com.meta.order.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseRequest(
        @NotNull(message = "Product is mandotary")
        Integer productId,
        @Positive(message = "Quantity is mandatory")
        double quantity
) {
}
