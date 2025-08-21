package com.example.order.service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponse(
        Long id,
        Long customerId,
        BigDecimal totalAmount,
        String status,
        LocalDateTime createdAt,
        List<OrderItemResponse> items
) {}
