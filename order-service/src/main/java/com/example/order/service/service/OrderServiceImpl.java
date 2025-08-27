package com.example.order.service.service;

import com.example.common.dto.CustomerResponse;
import com.example.common.dto.ProductResponse;
import com.example.order.service.dto.OrderItemResponse;
import com.example.order.service.dto.OrderRequest;
import com.example.order.service.dto.OrderResponse;
import com.example.order.service.entity.Order;
import com.example.order.service.entity.OrderItem;
import com.example.order.service.entity.OrderStatus;
import com.example.order.service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {


    @Qualifier("customerClient")
    private final WebClient customerClient;

    @Qualifier("productClient")
    private final WebClient productClient;

    private final OrderRepository  orderRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderRequest request) {
        if (request == null || request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order request must contain at least one item.");
        }
        if (request.getCustomerId() == null) {
            throw new IllegalArgumentException("CustomerId is required.");
        }

        CustomerResponse customer = customerClient.get()
                .uri("/api/customers/{id}", request.getCustomerId())
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .block();

        if (customer == null) {
            throw new IllegalArgumentException("Customer not found with id: " + request.getCustomerId());
        }

        var orderItems = new java.util.ArrayList<OrderItem>();
        var total = java.math.BigDecimal.ZERO;

        for (var it : request.getItems()) {
            ProductResponse product = productClient.get()
                    .uri("/api/products/{id}", it.getProductId())
                    .retrieve()
                    .bodyToMono(ProductResponse.class)
                    .block();

            if (product == null) {
                throw new IllegalArgumentException("Product not found with id: " + it.getProductId());
            }

            var unitPrice = product.price();
            var lineTotal = unitPrice.multiply(java.math.BigDecimal.valueOf(it.getQuantity()));
            total = total.add(lineTotal);

            var oi = new OrderItem();
            oi.setProductId(product.id());
            oi.setQuantity(it.getQuantity());
            oi.setUnitPrice(unitPrice);
            oi.setProductName(product.name());
            oi.setLineTotal(lineTotal);

            orderItems.add(oi);
        }


        var order = new Order();
        order.setCustomerId(customer.id());
        order.setCustomerEmail(customer.email());
        order.setStreet(customer.street());
        order.setCity(customer.city());
        order.setState(customer.state());
        order.setPostalCode(customer.postalCode());
        order.setCountry(customer.country());
        order.setTotalAmount(total);
        order.setStatus(OrderStatus.PENDING);

        for (var oi : orderItems) {
            oi.setOrder(order);
        }


        order.setItems(orderItems);

        order = orderRepository.save(order);


        java.util.List<OrderItemResponse> itemDtos = order.getItems().stream()
                .map(oi -> new OrderItemResponse(
                        oi.getProductId(),
                        oi.getProductName(),
                        oi.getUnitPrice(),
                        oi.getQuantity(),
                        oi.getLineTotal()

                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCreatedAt(),
                itemDtos
        );
    }

    @Override
    public OrderResponse findOrderById(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found: " + id);
                });

        var items = order.getItems().stream()
                .map(oi -> new OrderItemResponse(
                        oi.getProductId(),
                        oi.getProductName(),
                        oi.getUnitPrice(),
                        oi.getQuantity(),
                        oi.getLineTotal()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getCustomerId(),
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getCreatedAt(),
                items
        );
    }

    @Override
    public boolean deleteOrderById(Long id) {
        var order = orderRepository.findById(id)
                .orElseThrow(() -> {
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found: " + id);
                });

      orderRepository.delete(order);




        return true;
    }

    @Override
    public List<OrderResponse> getAllOrders() {//for yerine
        return orderRepository.findAll().stream()
                .map(order -> {
                    List<OrderItemResponse> items = order.getItems().stream()
                            .map(oi -> new OrderItemResponse(
                                    oi.getProductId(),
                                    oi.getProductName(),
                                    oi.getUnitPrice(),
                                    oi.getQuantity(),
                                    oi.getLineTotal()
                            ))
                            .toList();

                    return new OrderResponse(
                            order.getId(),
                            order.getCustomerId(),
                            order.getTotalAmount(),
                            order.getStatus().name(),
                            order.getCreatedAt(),
                            items
                    );
                })
                .toList();
    }

}
