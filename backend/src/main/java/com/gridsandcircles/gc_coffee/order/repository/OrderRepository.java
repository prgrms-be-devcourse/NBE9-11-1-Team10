package com.gridsandcircles.gc_coffee.order.repository;

import com.gridsandcircles.gc_coffee.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * [이슈 #33] 주문 상세 조회를 위해 Fetch Join을 사용합니다.
     * Order -> OrderItem -> Product를 한 번의 쿼리로 가져옵니다.
     * 이 부분은 솔직히 잘 몰라서 AI에게 들은 내용을 복붙했습니다.
     */
    @Query("select o from Order o " +
            "join fetch o.orderItems oi " +
            "join fetch oi.product " +
            "where o.id = :orderId")
    Optional<Order> findByIdWithDetails(@Param("orderId") Long orderId);
}