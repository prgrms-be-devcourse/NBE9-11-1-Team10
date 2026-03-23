package com.gridsandcircles.gc_coffee.order.service;

import com.gridsandcircles.gc_coffee.entity.Order;
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

    public Optional<OrderBatch> findCurrentBatch() {
        return orderBatchRepository.findByBatchDate(LocalDate.now());
    }

    public void deleteById(long id) {
        orderBatchRepository.deleteById(id);
    }

    // 주문의 orderedAt로 배치 날짜를 계산하고, 배치를 가져오거나 새로 만든다
    public OrderBatch findOrCreateByOrder(Order order) {
        LocalDateTime orderedAt = order.getOrderedAt();
        LocalDate batchDate = resolveBatchDate(orderedAt);
        return findOrCreateByBatchDate(batchDate);
    }

    // 실제 배치 엔티티를 생성하여 DB에 저장
    private OrderBatch createBatch(LocalDate batchDate) {
        LocalDateTime startAt = batchDate.atTime(BATCH_START_HOUR, 0);
        LocalDateTime endAt = batchDate.plusDays(1).atTime(BATCH_START_HOUR, 0);
        return orderBatchRepository.save(new OrderBatch(batchDate, startAt, endAt));
    }

    // 주문 시간이 14시 이전이면 전날 배치, 14시 이후면 당일 배치로 결정
    private LocalDate resolveBatchDate(LocalDateTime orderedAt) {
        if (orderedAt.getHour() < BATCH_START_HOUR) {
            return orderedAt.toLocalDate().minusDays(1);
        }
        return orderedAt.toLocalDate();
    }

    // 해당 날짜 배치가 있으면 반환, 없으면 생성해서 반환
    private OrderBatch findOrCreateByBatchDate(LocalDate batchDate) {
        return orderBatchRepository.findByBatchDate(batchDate)
                .orElseGet(() -> createBatch(batchDate));
    }
}
