package com.gridsandcircles.gc_coffee.order.repository;

import com.gridsandcircles.gc_coffee.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}