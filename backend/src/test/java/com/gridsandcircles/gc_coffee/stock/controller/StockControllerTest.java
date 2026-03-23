package com.gridsandcircles.gc_coffee.stock.controller;


import com.gridsandcircles.gc_coffee.entity.Product;
import com.gridsandcircles.gc_coffee.product.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class StockControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("재고 확인 성공 - 요청 수량이 재고보다 적거나 같음")
    void t1() throws Exception {
        // Given
        Product product = new Product("테스트 상품", 10000, 10);
        productRepository.save(product);
        Long productId = product.getId();
        int requestQuantity = 5; // 재고(10)보다 적은 요청

        // When
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/product/{productId}/stock", productId)
                                .param("requestQuantity", String.valueOf(requestQuantity))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value("true"));
    }

    @Test
    @DisplayName("재고 확인 실패 - 요청 수량이 재고보다 많음 (OUT_OF_STOCK)")
    void t2() throws Exception {
        // Given
        Product product = new Product("테스트 상품", 10000, 10);
        productRepository.save(product);
        Long productId = product.getId();
        int requestQuantity = 15; // 재고(10)보다 적은 요청

        // When
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/product/{productId}/stock", productId)
                                .param("requestQuantity", String.valueOf(requestQuantity))
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error.code").value("OUT_OF_STOCK")) // 에러 코드 확인
                .andExpect(jsonPath("$.error.message").value("재고가 부족합니다"));
    }

    @Test
    @DisplayName("재고 확인 실패 - 존재하지 않는 상품 ID 조회")
    void t3() throws Exception {
        // Given
        Product product = new Product("테스트 상품", 10000, 10);
        productRepository.save(product);
        Long invalidProductId = product.getId()+9L; //없는 아이디 호출

        // When
        ResultActions resultActions = mvc
                .perform(
                        get("/api/v1/product/{productId}/stock", invalidProductId)
                                .param("requestQuantity", "1")
                )
                .andDo(print());

        // Then
        resultActions
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error.code").value("PRODUCT_NOT_FOUND"))
                .andExpect(jsonPath("$.error.message").value("상품을 찾을 수 없습니다"));
    }

}
