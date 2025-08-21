package com.example.order.service.service;

import com.example.order.service.dto.OrderRequest;
import com.example.order.service.dto.OrderResponse;
import com.example.order.service.entity.Order;

import java.util.List;

public interface OrderService  {
    OrderResponse createOrder(OrderRequest req);
    OrderResponse findOrderById(Long id);
    OrderResponse deleteOrderById(Long id);
    List<OrderResponse> getAllOrders();

}
