package com.meta.order.payment;

import com.meta.order.customer.CustomerResponse;
import com.meta.order.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(

        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
