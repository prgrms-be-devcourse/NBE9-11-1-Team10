package com.gridsandcircles.gc_coffee.stock.service;

import com.gridsandcircles.gc_coffee.entity.Product;
import com.gridsandcircles.gc_coffee.global.exception.BusinessException;
import com.gridsandcircles.gc_coffee.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.transaction.annotation.Transactional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("재고 감소 성공 - 정상적인 수량이면 재고가 줄어든다")
    void decreaseStock_success() {
        // given (준비)
        int initialStock = 10;
        int decreaseQuantity = 3;

        Product product = new Product("우유", 1000, initialStock);
        productRepository.save(product);

        // when (실행)
        stockService.decreaseStock(product.getId(), decreaseQuantity);

        // then (검증)
        assertThat(product.getStock()).isEqualTo(7); // 10 - 3 = 7
    }

    @Test
    @DisplayName("재고 감소 실패 - 재고보다 많은 수량을 요청하면 예외가 발생한다")
    void decreaseStock_fail() {

        int initialStock = 5;
        int decreaseQuantity = 10; // 재고보다 많은 수량 요청

        Product product = new Product("우유", 1000, initialStock);
        productRepository.save(product);

        // when & then
        assertThatThrownBy(() -> stockService.decreaseStock(product.getId(),decreaseQuantity))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining("재고가 부족합니다");
    }

    @Test
    @DisplayName("재고 증가 성공 - 요청한 수량만큼 재고가 늘어난다")
    void increaseStock_success() {
        // given
        int initialStock = 10;
        int increaseQuantity = 5;

        Product product = new Product("우유", 1000, initialStock);
        productRepository.save(product);
        // when
        stockService.increaseStock(product.getId(), increaseQuantity);

        // then
        assertThat(product.getStock()).isEqualTo(15); // 10 + 5 = 15
    }

}
