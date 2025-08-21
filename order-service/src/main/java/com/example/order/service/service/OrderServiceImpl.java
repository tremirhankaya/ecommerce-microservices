package com.example.order.service.service;

import com.example.order.service.dto.OrderRequest;
import com.example.order.service.dto.OrderResponse;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    @Override
    public OrderResponse createOrder(OrderRequest req) {
        return null;
    }

    @Override
    public OrderResponse findOrderById(Long id) {
        return null;
    }

    @Override
    public OrderResponse deleteOrderById(Long id) {
        return null;
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return List.of();
    }
}
