package com.gridsandcircles.gc_coffee.orderBatchTest;

import com.gridsandcircles.gc_coffee.entity.OrderBatch;
import com.gridsandcircles.gc_coffee.order.dto.OrderBatchReq;
import com.gridsandcircles.gc_coffee.order.service.OrderBatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class OrderBatchControllerTest {

    @Autowired
    private OrderBatchService orderBatchService;

    @Test
    void create_batch_with_manual_values() {
        LocalDate batchDate = LocalDate.of(2026, 3, 21);
        LocalDateTime startAt = LocalDateTime.of(2026, 3, 21, 9, 0);
        LocalDateTime endAt = LocalDateTime.of(2026, 3, 21, 10, 0);

        OrderBatch orderBatch = orderBatchService.write(new OrderBatchReq(batchDate, startAt, endAt));

        assertThat(orderBatch.getId()).isNotNull();
        assertThat(orderBatch.getBatchDate()).isEqualTo(batchDate);
        assertThat(orderBatch.getStartAt()).isEqualTo(startAt);
        assertThat(orderBatch.getEndAt()).isEqualTo(endAt);
    }

}
