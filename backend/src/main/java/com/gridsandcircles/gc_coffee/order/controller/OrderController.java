package com.gridsandcircles.gc_coffee.order.controller;

import com.gridsandcircles.gc_coffee.order.dto.OrderCreateReq;
import com.gridsandcircles.gc_coffee.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Long> createOrder(@RequestBody @Valid OrderCreateReq req) {
        Long orderId = orderService.createOrder(req);
        return ResponseEntity.ok(orderId);
    }
}