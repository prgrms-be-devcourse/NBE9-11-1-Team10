package com.gridsandcircles.gc_coffee.orderBatchTest;

import com.gridsandcircles.gc_coffee.entity.Order;
import com.gridsandcircles.gc_coffee.entity.OrderBatch;
import com.gridsandcircles.gc_coffee.order.dto.OrderBatchRequest;
import com.gridsandcircles.gc_coffee.order.repository.OrderBatchRepository;
import com.gridsandcircles.gc_coffee.order.service.OrderBatchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Constructor;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class OrderBatchControllerTest {

    @Autowired
    private OrderBatchService orderBatchService;

    @Autowired
    private OrderBatchRepository orderBatchRepository;

    private List<OrderBatch> orderBatchList;

    @BeforeEach
    void setUp() {
        OrderBatch batch1 = orderBatchService.write(new OrderBatchRequest(
                LocalDate.of(2026, 3, 22),
                LocalDateTime.of(2026, 3, 22, 14, 0),
                LocalDateTime.of(2026, 3, 23, 14, 0)
        ));

        OrderBatch batch2 = orderBatchService.write(new OrderBatchRequest(
                LocalDate.of(2026, 3, 23),
                LocalDateTime.of(2026, 3, 23, 14, 0),
                LocalDateTime.of(2026, 3, 24, 14, 0)
        ));

        orderBatchList = List.of(batch1, batch2);
    }

    @Test
    void 주문_배치_작성() {
        OrderBatch created = orderBatchService.write(new OrderBatchRequest(
                LocalDate.of(2026, 3, 24),
                LocalDateTime.of(2026, 3, 24, 14, 0),
                LocalDateTime.of(2026, 3, 25, 14, 0)
        ));

        assertThat(created.getId()).isEqualTo(3);
        assertThat(created.getBatchDate()).isEqualTo(LocalDate.of(2026, 3, 24));
        assertThat(created.getStartAt()).isEqualTo(LocalDateTime.of(2026, 3, 24, 14, 0));
        assertThat(created.getEndAt()).isEqualTo(LocalDateTime.of(2026, 3, 25, 14, 0));
    }

    @Test
    void 주문_배치_다건_조회() {
        List<OrderBatch> all = orderBatchService.list();

        assertThat(all).hasSizeGreaterThanOrEqualTo(2);
        assertThat(all)
                .extracting(OrderBatch::getId)
                .contains(orderBatchList.get(0).getId(), orderBatchList.get(1).getId());
    }

    @Test
    void 주문_배치_단건_조회() {
        Long targetId = orderBatchList.get(0).getId();

        OrderBatch found = orderBatchService.findById(targetId).orElse(null);

        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(targetId);
        assertThat(found.getBatchDate()).isEqualTo(LocalDate.of(2026, 3, 22));
    }

    @Test
    void 주문_배치_삭제() {
        long targetId = orderBatchList.get(0).getId();
        orderBatchService.deleteById(targetId);

        assertThat(orderBatchService.findById(targetId)).isEmpty();
    }

    @Test
    void 주문_기준_배치_없으면_생성() throws Exception {
        LocalDateTime orderedAt = LocalDateTime.of(2026, 4, 1, 15, 0);
        LocalDate expectedBatchDate = LocalDate.of(2026, 4, 2);

        // expectedBatchDate 존재 시, 삭제
        orderBatchRepository.findByBatchDate(expectedBatchDate)
                .ifPresent(orderBatchRepository::delete);

        // Order 객체 생성
        Constructor<Order> constructor = Order.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Order order = constructor.newInstance();
        ReflectionTestUtils.setField(order, "orderedAt", orderedAt);

        OrderBatch orderBatch = orderBatchService.findOrCreateByOrder(order);

        // 자동 생성 확인
        assertThat(orderBatch.getBatchDate()).isEqualTo(expectedBatchDate);
        assertThat(orderBatch.getStartAt()).isEqualTo(expectedBatchDate.atTime(14, 0));
        assertThat(orderBatch.getEndAt()).isEqualTo(expectedBatchDate.plusDays(1).atTime(14, 0));
    }

}
