package com.gridsandcircles.gc_coffee.order.controller;

import com.gridsandcircles.gc_coffee.global.dto.ApiResponse;
import com.gridsandcircles.gc_coffee.order.dto.OrderCreateRequest;
import com.gridsandcircles.gc_coffee.order.dto.OrderDetailResponse;
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
    public ApiResponse<Long> createOrder(@RequestBody @Valid OrderCreateRequest req) {
        Long orderId = orderService.createOrder(req);

        return ApiResponse.ok(orderId);
    }

    @GetMapping("/{orderId}")
    public ApiResponse<OrderDetailResponse> getOrderDetails(@PathVariable("orderId") Long orderId) {
        OrderDetailResponse response = orderService.getOrderDetails(orderId);
        return ApiResponse.ok(response);
    }
}