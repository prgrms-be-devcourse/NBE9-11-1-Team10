package com.gridsandcircles.gc_coffee.order.service;

import com.gridsandcircles.gc_coffee.entity.OrderBatch;
import com.gridsandcircles.gc_coffee.order.dto.OrderBatchReq;
import com.gridsandcircles.gc_coffee.order.repository.OrderBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderBatchService {

    private final OrderBatchRepository orderBatchRepository;

    public List<OrderBatch> list(){
        return orderBatchRepository.findAll();
    }

    public OrderBatch write(OrderBatchReq req){
        OrderBatch orderBatch = new OrderBatch(req.batchDate(), req.startAt(), req.endAt());
        return orderBatchRepository.save(orderBatch);
    }

}
