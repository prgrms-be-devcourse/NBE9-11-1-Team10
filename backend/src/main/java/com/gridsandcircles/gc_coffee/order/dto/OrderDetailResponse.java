package com.gridsandcircles.gc_coffee.order.dto;

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
    // 주문에 포함된 개별 상품 정보를 담는 내부 record
    public record OrderItemDetail(
            Long productId,
            String productName, // Product 엔티티에서 가져올 이름
            int quantity,
            long unitPrice
    ) {}
}