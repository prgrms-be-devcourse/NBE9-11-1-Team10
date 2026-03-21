package com.gridsandcircles.gc_coffee.order.repository;

import com.gridsandcircles.gc_coffee.entity.OrderBatch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBatchRepository extends JpaRepository<OrderBatch, Long> {
}
