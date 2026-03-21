package com.gridsandcircles.gc_coffee.product.controller.display;

import com.gridsandcircles.gc_coffee.product.dto.display.ProductDisplayResponse;
import com.gridsandcircles.gc_coffee.product.service.display.ProductDisplayService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductDisplayController.class)
public class ProductDisplayControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductDisplayService productDisplayService;

    @MockitoBean
    JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    @DisplayName("검색어 없이 상품 목록을 조회한다")
    void getProducts_withoutKeyword() throws Exception {
        // given
        List<ProductDisplayResponse> mockProducts = List.of(
                new ProductDisplayResponse(1L, "에티오피아 예가체프 G1", 15000),
                new ProductDisplayResponse(2L, "과테말라 안티구아", 14000)
        );

        given(productDisplayService.findProducts(null)).willReturn(mockProducts);

        // when & then
        mockMvc.perform(get("/api/v1/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("에티오피아 예가체프 G1"))
                .andExpect(jsonPath("$.data[1].name").value("과테말라 안티구아"));
    }

    @Test
    @DisplayName("검색어로 상품 목록을 조회한다")
    void getProducts_withKeyword() throws Exception {
        // given
        String keyword = "에티오피아";
        List<ProductDisplayResponse> mockProducts = List.of(
                new ProductDisplayResponse(1L, "에티오피아 예가체프 G1", 15000)
        );

        given(productDisplayService.findProducts(keyword)).willReturn(mockProducts);

        // when & then
        mockMvc.perform(get("/api/v1/products").param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("에티오피아 예가체프 G1"))
                .andExpect(jsonPath("$.data.length()").value(1));
    }
}
