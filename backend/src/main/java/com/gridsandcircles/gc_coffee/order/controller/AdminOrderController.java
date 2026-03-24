package com.gridsandcircles.gc_coffee.order.controller;

import com.gridsandcircles.gc_coffee.entity.Order;
import com.gridsandcircles.gc_coffee.global.dto.ApiResponse;
import com.gridsandcircles.gc_coffee.order.dto.OrderListResponse;
import com.gridsandcircles.gc_coffee.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public ApiResponse<List<OrderListResponse>> list(){
        List<Order> orderList = orderService.list();

        List<OrderListResponse> orderListResponseList = orderList.reversed().stream()
                .map(OrderListResponse::new)
                .toList();

        return ApiResponse.ok(orderListResponseList);
    }
}
