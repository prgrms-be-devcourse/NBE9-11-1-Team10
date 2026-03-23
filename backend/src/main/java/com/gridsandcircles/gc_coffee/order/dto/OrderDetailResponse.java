package com.gridsandcircles.gc_coffee.order.dto;

import com.gridsandcircles.gc_coffee.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailResponse(
        Long orderId,
        String email,
        String address,
        String zipCode,
        long totalPrice,
        int totalQuantity,
        LocalDateTime orderedAt,
        List<OrderItemDetail> orderItems
) {
    public record OrderItemDetail(
            Long productId,
            String productName,
            int quantity,
            long unitPrice
    ) {}

    public OrderDetailResponse(Order order) {
        this(
                order.getId(),
                order.getMember() != null ? order.getMember().getEmail() : null,
                order.getAddress(),
                order.getZipCode(),
                order.getTotalPrice(),
                order.getTotalQuantity(),
                order.getOrderedAt(),
                List.of()
        );
    }
}
