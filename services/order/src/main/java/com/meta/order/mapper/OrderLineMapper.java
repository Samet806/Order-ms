package com.meta.order.mapper;

import com.meta.order.model.Order;
import com.meta.order.orderline.OrderLine;
import com.meta.order.orderline.OrderLineRequest;
import com.meta.order.orderline.OrderLineResponse;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest orderLineRequest) {
        return OrderLine.builder()
                .id(orderLineRequest.id())
                .quantity(orderLineRequest.quantity())
                .order(Order.builder()
                        .id(orderLineRequest.id())
                        .build())
                .productId(orderLineRequest.productId())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return  new OrderLineResponse(orderLine.getId(),orderLine.getQuantity());
    }
}
