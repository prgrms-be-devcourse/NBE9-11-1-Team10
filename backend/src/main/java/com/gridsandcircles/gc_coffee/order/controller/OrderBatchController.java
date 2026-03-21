package com.gridsandcircles.gc_coffee.order.controller;

import com.gridsandcircles.gc_coffee.entity.OrderBatch;
import com.gridsandcircles.gc_coffee.order.dto.OrderBatchRes;
import com.gridsandcircles.gc_coffee.order.service.OrderBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/batch")
public class OrderBatchController {

    private final OrderBatchService orderBatchService;

    @GetMapping
    public List<OrderBatchRes> list(){
        List<OrderBatch> orderBatchList = orderBatchService.list();

        List<OrderBatchRes> orderBatchResList = orderBatchList.reversed().stream()
                .map(OrderBatchRes::new)
                .toList();

        return orderBatchResList;
    }
}
