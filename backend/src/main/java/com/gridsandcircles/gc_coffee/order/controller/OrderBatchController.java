package com.gridsandcircles.gc_coffee.order.controller;

import com.gridsandcircles.gc_coffee.entity.OrderBatch;
import com.gridsandcircles.gc_coffee.order.dto.OrderBatchRequest;
import com.gridsandcircles.gc_coffee.order.dto.OrderBatchResponse;
import com.gridsandcircles.gc_coffee.order.service.OrderBatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/batch")
public class OrderBatchController {

    private final OrderBatchService orderBatchService;

    @PostMapping("/write")
    public OrderBatchResponse write(@RequestBody OrderBatchRequest req){
        OrderBatch saved = orderBatchService.write(req);
        return new OrderBatchResponse(saved);
    }

    @GetMapping
    public List<OrderBatchResponse> list(){
        List<OrderBatch> orderBatchList = orderBatchService.list();

        List<OrderBatchResponse> orderBatchResponseList = orderBatchList.reversed().stream()
                .map(OrderBatchResponse::new)
                .toList();

        return orderBatchResponseList;
    }

    @GetMapping("{orderBatchId}")
    public OrderBatchResponse detail(@PathVariable Long orderBatchId) {
        OrderBatch orderBatch = orderBatchService.findById(orderBatchId).get();
        return new OrderBatchResponse(orderBatch);
    }

    @GetMapping("/current")
    public ResponseEntity<OrderBatchResponse> current() {
        return orderBatchService.findCurrentBatch()
                .map(orderBatch -> ResponseEntity.ok(new OrderBatchResponse(orderBatch)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{orderBatchId}")
    public ResponseEntity<Void> delete(@PathVariable long orderBatchId) {
        orderBatchService.deleteById(orderBatchId);
        return ResponseEntity.noContent().build(); // 삭제 후 204응답
    }
}
