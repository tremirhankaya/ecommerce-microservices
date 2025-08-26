package com.example.order.service.service;

import com.example.common.dto.CustomerResponse;
import com.example.order.service.dto.OrderRequest;
import com.example.order.service.dto.OrderResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    @Qualifier("customerClient")
    private final WebClient customerClient;

    @Qualifier("productClient")
    private final WebClient productClient;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        // 1) Input kontrol
        if (request == null || request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order request must contain at least one item.");
        }
        if (request.getCustomerId() == null) {
            throw new IllegalArgumentException("CustomerId is required.");
        }

        // 2) Servisten bilgi müşteri bilgisi çekme
        CustomerResponse customer = customerClient.get()
                .uri("/api/customers/{id}", request.getCustomerId())
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .block();

        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with id: " + request.getCustomerId());
        }



        return null; // temporary
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
