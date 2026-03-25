package com.gridsandcircles.gc_coffee.order.dto;

import com.gridsandcircles.gc_coffee.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderListResponse(
        Long orderId,
        String email,
        String batchDate,
        String address,
        String zipCode,
        int totalPrice,
        int totalQuantity,
        LocalDateTime orderedAt,
        List<OrderItemDetail> orderItems
) {
    public OrderListResponse(Order order) {
        this(
                order.getId(),
                order.getMember().getEmail(),
                order.getOrderBatch().getBatchDate().toString(),
                order.getAddress(),
                order.getZipCode(),
                order.getTotalPrice(),
                order.getTotalQuantity(),
                order.getOrderedAt(),
                order.getOrderItems().stream()
                        .map(item -> new OrderItemDetail(
                                item.getProduct().getId(),
                                item.getProduct().getName(),
                                item.getQuantity(),
                                item.getProduct().getPrice()
                        ))
                        .toList()
        );
    }

    public record OrderItemDetail(
            Long productId,
            String productName,
            int quantity,
            int unitPrice
    ) {}
}