package com.gridsandcircles.gc_coffee.order.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderBatchReq(
        LocalDate batchDate,
        LocalDateTime startAt,
        LocalDateTime endAt
) {
}
