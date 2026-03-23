package com.gridsandcircles.gc_coffee.order.service;

import com.gridsandcircles.gc_coffee.order.repository.OrderRepository;
import com.gridsandcircles.gc_coffee.product.repository.ProductRepository;
import com.gridsandcircles.gc_coffee.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createOrder() {
        return null;
    }

    public void getOrderDetails(Long orderId) {
    }
}