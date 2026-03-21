package com.gridsandcircles.gc_coffee.order.dto;

import com.gridsandcircles.gc_coffee.entity.OrderBatch;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderBatchRes (
        Long id,
        LocalDate batchDate,
        LocalDateTime startAt,
        LocalDateTime endAt
) {
    public OrderBatchRes(OrderBatch orderBatch) {
        this(
                orderBatch.getId(),
                orderBatch.getBatchDate(),
                orderBatch.getStartAt(),
                orderBatch.getEndAt()
        );
    }
}

