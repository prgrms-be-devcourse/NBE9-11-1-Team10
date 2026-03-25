package com.gridsandcircles.gc_coffee.order.repository;

import com.gridsandcircles.gc_coffee.entity.OrderBatch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface OrderBatchRepository extends JpaRepository<OrderBatch, Long> {
    Optional<OrderBatch> findByBatchDate(LocalDate batchDate);
}
