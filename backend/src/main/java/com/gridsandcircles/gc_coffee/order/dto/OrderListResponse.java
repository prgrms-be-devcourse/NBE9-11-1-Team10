package com.gridsandcircles.gc_coffee.order.dto;

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
    public record OrderItemDetail(
            Long productId,
            String productName,
            int quantity,
            int unitPrice
    ) {}
}