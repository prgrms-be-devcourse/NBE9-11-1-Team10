package com.gridsandcircles.gc_coffee.order.service;

import com.gridsandcircles.gc_coffee.entity.OrderBatch;
import com.gridsandcircles.gc_coffee.order.dto.OrderBatchRequest;
import com.gridsandcircles.gc_coffee.order.repository.OrderBatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderBatchService {

    private static final int BATCH_START_HOUR = 14;

    private final OrderBatchRepository orderBatchRepository;

    public List<OrderBatch> list(){
        return orderBatchRepository.findAll();
    }

    public Optional<OrderBatch> findById(Long id){
        return orderBatchRepository.findById(id);
    }

    public OrderBatch write(OrderBatchRequest req){
        OrderBatch orderBatch = new OrderBatch(req.batchDate(), req.startAt(), req.endAt());
        return orderBatchRepository.save(orderBatch);
    }

    public OrderBatch findOrCreateCurrentBatch() {
        LocalDate batchDate = LocalDate.now();

        return orderBatchRepository.findByBatchDate(batchDate)
                .orElseGet(() -> {
                    LocalDateTime startAt = batchDate.atTime(BATCH_START_HOUR, 0);
                    LocalDateTime endAt = batchDate.plusDays(1).atTime(BATCH_START_HOUR, 0);
                    return orderBatchRepository.save(new OrderBatch(batchDate, startAt, endAt));
                });
    }

}
