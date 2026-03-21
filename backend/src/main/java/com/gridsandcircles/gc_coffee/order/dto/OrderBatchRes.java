package com.gridsandcircles.gc_coffee.order.dto;

import com.gridsandcircles.gc_coffee.entity.OrderBatch;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderBatchRes {

    private Long id;
    private LocalDate batchDate;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public static OrderBatchRes from(OrderBatch orderBatch){
        return new OrderBatchRes(
                orderBatch.getId(),
                orderBatch.getBatchDate(),
                orderBatch.getStartAt(),
                orderBatch.getEndAt()
        );
    }
}
