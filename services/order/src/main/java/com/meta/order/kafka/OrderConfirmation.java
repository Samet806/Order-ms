package com.meta.order.kafka;

import com.meta.order.customer.CustomerResponse;
import com.meta.order.model.PaymentMethod;
import com.meta.order.product.ProductPurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<ProductPurchaseResponse> product
) {
}
