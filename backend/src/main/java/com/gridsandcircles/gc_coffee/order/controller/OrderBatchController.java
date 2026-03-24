package com.gridsandcircles.gc_coffee.order.controller;

import com.gridsandcircles.gc_coffee.entity.OrderBatch;
import com.gridsandcircles.gc_coffee.global.dto.ApiResponse;
import com.gridsandcircles.gc_coffee.global.exception.BusinessException;
import com.gridsandcircles.gc_coffee.global.exception.ErrorCode;
import com.gridsandcircles.gc_coffee.order.dto.OrderBatchRequest;
import com.gridsandcircles.gc_coffee.order.dto.OrderBatchResponse;
import com.gridsandcircles.gc_coffee.order.service.OrderBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/batch")
public class OrderBatchController {

    private final OrderBatchService orderBatchService;

    @PostMapping
    public ApiResponse<OrderBatchResponse> write(@RequestBody OrderBatchRequest req){
        OrderBatch saved = orderBatchService.write(req);
        return ApiResponse.ok(new OrderBatchResponse(saved));
    }

    @GetMapping
    public ApiResponse<List<OrderBatchResponse>> list(){
        List<OrderBatch> orderBatchList = orderBatchService.list();

        List<OrderBatchResponse> orderBatchResponseList = orderBatchList.reversed().stream()
                .map(OrderBatchResponse::new)
                .toList();

        return ApiResponse.ok(orderBatchResponseList);
    }

    @GetMapping("{orderBatchId}")
    public ApiResponse<OrderBatchResponse> detail(@PathVariable Long orderBatchId) {
        OrderBatch orderBatch = orderBatchService.findById(orderBatchId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_BATCH_NOT_FOUND));
        return ApiResponse.ok(new OrderBatchResponse(orderBatch));
    }

    @GetMapping("/current")
    public ApiResponse<OrderBatchResponse> current() {
        OrderBatch batch = orderBatchService.findCurrentBatch()
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_BATCH_NOT_FOUND));
        return ApiResponse.ok(new OrderBatchResponse(batch));
    }

    @DeleteMapping("{orderBatchId}")
    public ApiResponse<Void> delete(@PathVariable long orderBatchId) {
        orderBatchService.deleteById(orderBatchId);
        return ApiResponse.ok();
    }
}
