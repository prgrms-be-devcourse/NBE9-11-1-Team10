package com.gridsandcircles.gc_coffee.order.service;

import com.gridsandcircles.gc_coffee.entity.OrderBatch;
import com.gridsandcircles.gc_coffee.order.dto.OrderBatchRes;
import com.gridsandcircles.gc_coffee.order.repository.OrderBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderBatchService {

    private final OrderBatchRepository orderBatchRepository;

    public List<OrderBatchRes> list(){
        List<OrderBatch> orderBatchList = orderBatchRepository.findAll();

        return orderBatchList.stream()
                .map(OrderBatchRes::from)
                .toList();
    }
}
