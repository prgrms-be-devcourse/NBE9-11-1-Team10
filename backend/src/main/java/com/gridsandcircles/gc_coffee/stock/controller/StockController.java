package com.gridsandcircles.gc_coffee.stock.controller;


import com.gridsandcircles.gc_coffee.global.dto.ApiResponse;
import com.gridsandcircles.gc_coffee.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    //api/v1/product/{productId}/stock?requestQuantity=재고수량
    @GetMapping("api/v1/product/{productId}/stock")
    public ResponseEntity<ApiResponse<Void>> checkStock(
            @PathVariable("productId") Long productId,
            @RequestParam(required = false,defaultValue = "0") int requestQuantity
    ) {

        //고객이 +로 장바구니에 추가된 상품의 "번호"와 상품의 "재고 수량"이 필요하다.
        stockService.validateStockAvailability(productId,requestQuantity);

        return ResponseEntity.ok(ApiResponse.ok());
    }

}
