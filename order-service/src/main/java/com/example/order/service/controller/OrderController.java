package com.example.order.service.controller;

import com.example.order.service.dto.OrderRequest;
import com.example.order.service.dto.OrderResponse;
import com.example.order.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.crypto.spec.OAEPParameterSpec;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderService orderService;


    @PostMapping
    public OrderResponse createOrder(@RequestBody OrderRequest orderRequest) {
        OrderResponse orderResponse = orderService.createOrder(orderRequest);
        return orderResponse;
    }

    @GetMapping("/{id}")
    public OrderResponse findById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }



}
