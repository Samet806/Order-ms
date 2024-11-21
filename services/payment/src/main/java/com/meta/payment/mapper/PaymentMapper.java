package com.meta.payment.mapper;

import com.meta.payment.dto.PaymentRequest;
import com.meta.payment.model.Payment;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentMapper {
    public Payment toPayment(PaymentRequest paymentRequest) {

        return Payment.builder()
                .id(paymentRequest.id())
                .paymentMethod(paymentRequest.paymentMethod())
                .amount(paymentRequest.amount())
                .orderId(paymentRequest.orderId())
                .build();
    }
}
