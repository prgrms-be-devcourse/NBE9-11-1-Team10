package com.gridsandcircles.gc_coffee.order.dto;

import com.gridsandcircles.gc_coffee.entity.Order;

import java.time.LocalDateTime;

public record OrderListResponse(
        Long orderId,
        Long memberId,
        Long batchId,
        String address,
        String zipCode,
        LocalDateTime orderedAt,
        int totalPrice,
        int totalQuantity
) {
    public OrderListResponse(Order order) {
        this(
                order.getId(),
                order.getMember() != null ? order.getMember().getId() : null,
                order.getOrderBatch() != null ? order.getOrderBatch().getId() : null,
                order.getAddress(),
                order.getZipCode(),
                order.getOrderedAt(),
                order.getTotalPrice(),
                order.getTotalQuantity()
        );
    }
}
