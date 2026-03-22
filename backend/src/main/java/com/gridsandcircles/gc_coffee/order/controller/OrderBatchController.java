package com.gridsandcircles.gc_coffee.order.controller;

import com.gridsandcircles.gc_coffee.entity.OrderBatch;
import com.gridsandcircles.gc_coffee.order.dto.OrderBatchReq;
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

    @PostMapping("/write")
    public OrderBatchRes write(@RequestBody OrderBatchReq req){
        OrderBatch saved = orderBatchService.write(req);
        return new OrderBatchRes(saved);
    }

    @GetMapping
    public List<OrderBatchRes> list(){
        List<OrderBatch> orderBatchList = orderBatchService.list();

        List<OrderBatchRes> orderBatchResList = orderBatchList.reversed().stream()
                .map(OrderBatchRes::new)
                .toList();

        return orderBatchResList;
    }

    @GetMapping("{orderBatchId}")
    public OrderBatchRes detail(@PathVariable Long orderBatchId) {
        OrderBatch orderBatch = orderBatchService.findById(orderBatchId).get();
        return new OrderBatchRes(orderBatch);
    }
}
