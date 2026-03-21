package com.gridsandcircles.gc_coffee.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate batchDate;

    @Column(nullable = false)
    private LocalDateTime startAt;

    @Column(nullable = false)
    private LocalDateTime endAt;

    public OrderBatch(LocalDate batchDate, LocalDateTime startAt, LocalDateTime endAt) {
        this.batchDate = batchDate;
        this.startAt = startAt;
        this.endAt = endAt;
    }

}
