package com.gridsandcircles.gc_coffee.product.controller;

import com.gridsandcircles.gc_coffee.global.dto.ApiResponse;
import com.gridsandcircles.gc_coffee.product.dto.ProductCreateRequest;
import com.gridsandcircles.gc_coffee.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
public class AdminProductController {

    private final ProductService productService;

    @PostMapping
    public ApiResponse<Long> createProduct(@RequestBody ProductCreateRequest request) {
        Long productId = productService.createProduct(request);

        return ApiResponse.ok(productId);
    }
}