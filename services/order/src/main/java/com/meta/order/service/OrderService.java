package com.meta.order.service;

import com.meta.order.customer.CustomerClient;
import com.meta.order.dto.OrderRequest;
import com.meta.order.dto.OrderResponse;
import com.meta.order.exception.BusinessException;
import com.meta.order.kafka.OrderConfirmation;
import com.meta.order.kafka.OrderProducer;
import com.meta.order.mapper.OrderMapper;
import com.meta.order.orderline.OrderLineRequest;
import com.meta.order.payment.PaymentClient;
import com.meta.order.payment.PaymentRequest;
import com.meta.order.product.ProductClient;
import com.meta.order.product.PurchaseRequest;
import com.meta.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderMapper mapper;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private  final PaymentClient paymentClient;

    public Integer createOrder(OrderRequest orderRequest) {

        //check customer
        var customer = customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(()->new BusinessException("Cannot create order::No Customer exists with the provided Id:"+orderRequest.customerId()));


        //purchase products - product ms
       var purchasedProducts= productClient.purchaseProducts(orderRequest.products());

        //persist order
        var order = this.orderRepository.save(mapper.toOrder(orderRequest));

       // persist orderline
        for(PurchaseRequest purchaseRequest:orderRequest.products()){
            orderLineService.saveOrderLine(new OrderLineRequest(
                    null,
                    order.getId(),
                    purchaseRequest.productId(),
                    purchaseRequest.quantity()));

        }

        // todo start payment process
        paymentClient.requestOrderPayment(
                new PaymentRequest(orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        order.getId(),
                        order.getReference(),
                       customer )
        );

        //send the order confirmation - notification kafka
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        orderRequest.reference(),
                        orderRequest.amount(),
                        orderRequest.paymentMethod(),
                        customer,
                        purchasedProducts
                )
        );
        return  order.getId();
    }

    public List<OrderResponse> findAll() {
        return  orderRepository.findAll().stream()
                .map(mapper::fromOrder).toList();
    }

    public OrderResponse findOrderById(Integer orderId) {
         return orderRepository.findById(orderId)
                 .map(mapper::fromOrder)
                 .orElseThrow(()-> new BusinessException(String.format("No order found with the provided ID: %d",orderId)));
    }
}
