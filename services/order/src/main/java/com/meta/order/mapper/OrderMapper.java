package com.meta.order.mapper;

import com.meta.order.dto.OrderRequest;
import com.meta.order.dto.OrderResponse;
import com.meta.order.model.Order;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderMapper {

    public Order toOrder(OrderRequest orderRequest) {

        return Order.builder()
                .id(orderRequest.id())
                .paymentMethod(orderRequest.paymentMethod())
                .reference(orderRequest.reference())
                .customerId(orderRequest.customerId())
                .totalAmount(orderRequest.amount())
                .build();
    }

    public OrderResponse fromOrder(Order order) {
        return  new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getCustomerId()
        );
    }
}
