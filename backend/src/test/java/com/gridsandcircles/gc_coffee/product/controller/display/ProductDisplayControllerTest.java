package com.gridsandcircles.gc_coffee.product.controller.display;

import com.gridsandcircles.gc_coffee.product.controller.ProductDisplayController;
import com.gridsandcircles.gc_coffee.product.dto.PageResponse;
import com.gridsandcircles.gc_coffee.product.dto.ProductResponse;
import com.gridsandcircles.gc_coffee.product.service.ProductDisplayService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
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
        PageResponse<ProductResponse> mockPageResponse = new PageResponse<>(
                List.of(
                        new ProductResponse(1L, "에티오피아 예가체프 G1", 15000),
                        new ProductResponse(2L, "과테말라 안티구아", 14000)
                ),
                0,
                4,
                1,
                2,
                true,
                true
        );

        given(productDisplayService.findProducts(eq(null), any(Pageable.class)))
                .willReturn(mockPageResponse);

        // when & then
        mockMvc.perform(get("/api/v1/products"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content[0].id").value(1))
                .andExpect(jsonPath("$.data.content[0].name").value("에티오피아 예가체프 G1"))
                .andExpect(jsonPath("$.data.content[1].name").value("과테말라 안티구아"))
                .andExpect(jsonPath("$.data.page").value(0))
                .andExpect(jsonPath("$.data.size").value(4));
    }

    @Test
    @DisplayName("검색어로 상품 목록을 조회한다")
    void getProducts_withKeyword() throws Exception {
        // given
        String keyword = "에티오피아";

        PageResponse<ProductResponse> mockPageResponse = new PageResponse<>(
                List.of(
                        new ProductResponse(1L, "에티오피아 예가체프 G1", 15000)
                ),
                0,
                4,
                1,
                1,
                true,
                true
        );

        given(productDisplayService.findProducts(eq(keyword), any(Pageable.class)))
                .willReturn(mockPageResponse);

        // when & then
        mockMvc.perform(get("/api/v1/products").param("keyword", keyword))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].id").value(1))
                .andExpect(jsonPath("$.data.content[0].name").value("에티오피아 예가체프 G1"))
                .andExpect(jsonPath("$.data.content.length()").value(1))
                .andExpect(jsonPath("$.data.page").value(0))
                .andExpect(jsonPath("$.data.size").value(4));
    }

    @Test
    @DisplayName("페이징 조건으로 상품 목록을 조회한다")
    void getProducts_withPaging() throws Exception {
        // given
        PageResponse<ProductResponse> mockPageResponse = new PageResponse<>(
                List.of(
                        new ProductResponse(5L, "케냐 AA", 16000),
                        new ProductResponse(6L, "콜롬비아 수프리모", 15500)
                ),
                1,
                2,
                3,
                6,
                false,
                false
        );

        given(productDisplayService.findProducts(eq(null), any(Pageable.class)))
                .willReturn(mockPageResponse);

        // when & then
        mockMvc.perform(get("/api/v1/products")
                        .param("page", "1")
                        .param("size", "2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].id").value(5))
                .andExpect(jsonPath("$.data.content[0].name").value("케냐 AA"))
                .andExpect(jsonPath("$.data.content[1].name").value("콜롬비아 수프리모"))
                .andExpect(jsonPath("$.data.page").value(1))
                .andExpect(jsonPath("$.data.size").value(2))
                .andExpect(jsonPath("$.data.totalPages").value(3))
                .andExpect(jsonPath("$.data.totalElements").value(6))
                .andExpect(jsonPath("$.data.first").value(false))
                .andExpect(jsonPath("$.data.last").value(false));
    }

    @Test
    @DisplayName("가격 오름차순으로 상품 목록을 조회한다")
    void getProducts_withPriceAscSort() throws Exception {
        // given
        PageResponse<ProductResponse> mockPageResponse = new PageResponse<>(
                List.of(
                        new ProductResponse(2L, "과테말라 안티구아", 14000),
                        new ProductResponse(1L, "에티오피아 예가체프 G1", 15000)
                ),
                0,
                4,
                1,
                2,
                true,
                true
        );

        given(productDisplayService.findProducts(eq(null), any(Pageable.class)))
                .willReturn(mockPageResponse);

        // when & then
        mockMvc.perform(get("/api/v1/products")
                        .param("sortBy", "price")
                        .param("direction", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].name").value("과테말라 안티구아"))
                .andExpect(jsonPath("$.data.content[0].price").value(14000))
                .andExpect(jsonPath("$.data.content[1].name").value("에티오피아 예가체프 G1"))
                .andExpect(jsonPath("$.data.content[1].price").value(15000));

        verify(productDisplayService).findProducts(eq(null), argThat(p ->
                p.getSort().getOrderFor("price") != null &&
                        p.getSort().getOrderFor("price").isAscending()
        ));
    }

    @Test
    @DisplayName("가격 내림차순으로 상품 목록을 조회한다")
    void getProducts_withPriceDescSort() throws Exception {
        // given
        PageResponse<ProductResponse> mockPageResponse = new PageResponse<>(
                List.of(
                        new ProductResponse(1L, "에티오피아 예가체프 G1", 15000),
                        new ProductResponse(2L, "과테말라 안티구아", 14000)
                ),
                0,
                4,
                1,
                2,
                true,
                true
        );

        given(productDisplayService.findProducts(eq(null), any(Pageable.class)))
                .willReturn(mockPageResponse);

        // when & then
        mockMvc.perform(get("/api/v1/products")
                        .param("sortBy", "price")
                        .param("direction", "desc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data.content[0].name").value("에티오피아 예가체프 G1"))
                .andExpect(jsonPath("$.data.content[0].price").value(15000))
                .andExpect(jsonPath("$.data.content[1].name").value("과테말라 안티구아"))
                .andExpect(jsonPath("$.data.content[1].price").value(14000));

        verify(productDisplayService).findProducts(eq(null), argThat(p ->
                p.getSort().getOrderFor("price") != null &&
                        p.getSort().getOrderFor("price").isDescending()
        ));
    }
}
