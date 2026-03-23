package com.gridsandcircles.gc_coffee.order.service;

import com.gridsandcircles.gc_coffee.entity.*;
import com.gridsandcircles.gc_coffee.order.dto.OrderCreateRequest;
import com.gridsandcircles.gc_coffee.order.dto.OrderDetailResponse;
import com.gridsandcircles.gc_coffee.order.repository.OrderRepository;
import com.gridsandcircles.gc_coffee.order.repository.OrderBatchRepository;
import com.gridsandcircles.gc_coffee.product.repository.ProductRepository;
//import com.gridsandcircles.gc_coffee.member.repository.MemberRepository;
import com.gridsandcircles.gc_coffee.global.exception.BusinessException;
import com.gridsandcircles.gc_coffee.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderBatchService orderBatchService;

    @Transactional
    public Long createOrder(OrderCreateRequest req) {
        // 1. 주문 시점 확정
        LocalDateTime orderedAt = LocalDateTime.now(ZoneId.of("Asia/Seoul"));

        // 2. 배송 배치 자동 결정
        // 임시 Order 객체를 만들어 BatchService에 넘겨 배치를 결정
        Order tempOrder = Order.builder().orderedAt(orderedAt).build();
        OrderBatch orderBatch = orderBatchService.findOrCreateByOrder(tempOrder);

        int totalPrice = 0;
        int totalQuantity = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        // 3. 주문 상세 항목 생성 및 재고 차감
        for (OrderCreateRequest.OrderItemReq itemReq : req.orderItems()) {
            Product product = productRepository.findById(itemReq.productId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

            product.removeStock(itemReq.quantity());

            orderItems.add(OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.quantity())
                    .build());

            totalPrice += product.getPrice() * itemReq.quantity();
            totalQuantity += itemReq.quantity();
        }

        // 4. 최종 주문 생성
        Order order = Order.builder()
                .orderBatch(orderBatch) // 자동으로 찾은 배치 주입
                .address(req.address())
                .zipCode(req.zipCode())
                .orderedAt(orderedAt)
                .totalPrice(totalPrice)
                .totalQuantity(totalQuantity)
                .orderItems(new ArrayList<>())
                .build();

        // 5. 양방향 관계 설정
        for (OrderItem item : orderItems) {
            item.assignOrder(order);
            order.getOrderItems().add(item);
        }

        return orderRepository.save(order).getId();
    }

    @Transactional(readOnly = true)
    public OrderDetailResponse getOrderDetails(Long orderId) {
        Order order = orderRepository.findByIdWithDetails(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));

        List<OrderDetailResponse.OrderItemDetail> itemDetails = order.getOrderItems().stream()
                .map(item -> new OrderDetailResponse.OrderItemDetail(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getPrice() // OrderItem에 unitPrice가 없으므로 Product에서 가져옴
                ))
                .toList();

        return new OrderDetailResponse(
                order.getId(),
                order.getMember().getEmail(), // Order에서 Member를 거쳐 Email 조회
                order.getAddress(),
                order.getZipCode(),
                order.getTotalPrice(),
                order.getTotalQuantity(),
                order.getOrderedAt(),
                itemDetails
        );
    }
}