package com.gridsandcircles.gc_coffee.order.controller;

import com.gridsandcircles.gc_coffee.entity.Order;
import com.gridsandcircles.gc_coffee.global.dto.ApiResponse;
import com.gridsandcircles.gc_coffee.order.dto.OrderCreateRequest;
import com.gridsandcircles.gc_coffee.order.dto.OrderDetailResponse;
import com.gridsandcircles.gc_coffee.order.dto.OrderListResponse;
import com.gridsandcircles.gc_coffee.order.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public ApiResponse<List<OrderListResponse>> list(){
        List<Order> orderList = orderService.list();

        List<OrderListResponse> orderListResponseList = orderList.reversed().stream()
                .map(OrderListResponse::new)
                .toList();

        return ApiResponse.ok(orderListResponseList);
    }
}
