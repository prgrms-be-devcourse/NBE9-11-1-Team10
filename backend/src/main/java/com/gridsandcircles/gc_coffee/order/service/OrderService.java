package com.gridsandcircles.gc_coffee.order.service;

import com.gridsandcircles.gc_coffee.entity.*;
import com.gridsandcircles.gc_coffee.order.dto.OrderCreateRequest;
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
import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
//    private final MemberRepository memberRepository;
    private final OrderBatchRepository orderBatchRepository;

    @Transactional
    public Long createOrder(OrderCreateRequest req) {
        // 1. 배송 회차 조회
        OrderBatch orderBatch = orderBatchRepository.findById(1L)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_BATCH_NOT_FOUND));

        // 2. 총 금액(totalPrice)과 수량(totalQuantity) 계산용 변수
        int totalPrice = 0;
        int totalQuantity = 0;
        List<OrderItem> orderItems = new ArrayList<>();

        // 3. 주문 상세 항목 생성
        for (OrderCreateRequest.OrderItemReq itemReq : req.orderItems()) {
            Product product = productRepository.findById(itemReq.productId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

            product.removeStock(itemReq.quantity()); // 재고 차감

            orderItems.add(OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.quantity())
                    .build());

            totalPrice += product.getPrice() * itemReq.quantity();
            totalQuantity += itemReq.quantity();
        }

        // 4. 최종 주문 생성
        Order order = Order.builder()
                // .member(tempMember)
                .orderBatch(orderBatch)
                .address(req.address())
                .zipCode(req.zipCode())
                .orderedAt(LocalDateTime.now())
                .totalPrice(totalPrice)
                .totalQuantity(totalQuantity)
                .orderItems(orderItems) // CascadeType.ALL을 위해 추가
                .build();

        // 5. 양방향 관계 설정 (OrderItem에 Order 주입)
        for (OrderItem item : orderItems) {
        }

        return orderRepository.save(order).getId();
    }
}