package com.gridsandcircles.gc_coffee.entity;

import com.gridsandcircles.gc_coffee.global.exception.BusinessException;
import com.gridsandcircles.gc_coffee.global.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int stock;

    public Product(String name, int price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // 재고 감소 로직
    public void removeStock(int quantity) {
        //전체 재고-들어온 수량
        int restStock = this.stock - quantity;
        if (restStock < 0) {
            throw new BusinessException(ErrorCode.OUT_OF_STOCK);
        }
        //전체 재고를 줄어든 재고의 양으로 최신화한다.
        this.stock = restStock;
    }
    // 재고 증가 로직
    public void addStock(int quantity) {
        //전체 재고를 들어온 quantity만큼 늘린다.
        this.stock += quantity;
    }
}
