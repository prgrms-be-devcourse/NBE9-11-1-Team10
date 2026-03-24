package com.gridsandcircles.gc_coffee.order.controller;

import com.gridsandcircles.gc_coffee.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/orders")
public class AdminOrderController {

    private final OrderService orderService;

//    @GetMapping
//    public ApiResponse<List<OrderListResponse>> list(){
//        List<Order> orderList = orderService.list();
//
//        List<OrderListResponse> orderListResponseList = orderList.reversed().stream()
//                .map(OrderListResponse::new)
//                .toList();
//
//        return ApiResponse.ok(orderListResponseList);
//    }
}
