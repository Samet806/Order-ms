package com.meta.order.service;

import com.meta.order.mapper.OrderLineMapper;
import com.meta.order.orderline.OrderLineRequest;
import com.meta.order.orderline.OrderLineResponse;
import com.meta.order.repository.OrderLineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderLineRepository orderLineRepository;
    private OrderLineMapper orderLineMapper;

    public Integer saveOrderLine(OrderLineRequest orderLineRequest) {
        var orderline =orderLineMapper.toOrderLine(orderLineRequest);
        return  orderLineRepository.save(orderline).getId();
    }

    public List<OrderLineResponse> findByOrderId(Integer orderId) {

        return  orderLineRepository.findAllByOrderId(orderId)
                .stream().map(orderLineMapper::toOrderLineResponse)
                .toList();
    }
}
