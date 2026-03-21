package com.gridsandcircles.gc_coffee.product.controller.display;

import com.gridsandcircles.gc_coffee.global.dto.ApiResponse;
import com.gridsandcircles.gc_coffee.product.dto.display.ProductDisplayResponse;
import com.gridsandcircles.gc_coffee.product.service.display.ProductDisplayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductDisplayController {
    private final ProductDisplayService productDisplayService;

    @GetMapping
    public ApiResponse<List<ProductDisplayResponse>> getProducts(@RequestParam(required = false) String keyword) {
        return ApiResponse.ok(productDisplayService.findProducts(keyword));
    }
}
