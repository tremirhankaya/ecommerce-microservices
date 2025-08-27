package com.example.order.service.controller;

import com.example.order.service.dto.OrderRequest;
import com.example.order.service.dto.OrderResponse;
import com.example.order.service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Long id) {
        orderService.deleteOrderById(id);
        return ResponseEntity.ok("Order deleted");

    }

    @GetMapping
    public List<OrderResponse> findAllOrders() {
        return  orderService.getAllOrders();
    }

}
