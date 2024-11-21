package com.meta.order.controlller;


import com.meta.order.dto.OrderRequest;
import com.meta.order.dto.OrderResponse;
import com.meta.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Integer> createOrder(@RequestBody @Valid OrderRequest orderRequest){
        return new ResponseEntity<>(orderService.createOrder(orderRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> findAll(){
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> findOrderById(@PathVariable Integer orderId){
        return ResponseEntity.ok(orderService.findOrderById(orderId));
    }
}
