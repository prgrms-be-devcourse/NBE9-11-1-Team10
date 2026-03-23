package com.gridsandcircles.gc_coffee.product.controller;

import com.gridsandcircles.gc_coffee.global.dto.ApiResponse;
import com.gridsandcircles.gc_coffee.product.dto.ProductCreateRequest;
import com.gridsandcircles.gc_coffee.product.dto.ProductCreateResponse;
import com.gridsandcircles.gc_coffee.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
public class AdminProductController {

    private final ProductService productService;

    @PostMapping
    public ApiResponse<ProductCreateResponse> createProduct(@RequestBody ProductCreateRequest request) {
        ProductCreateResponse response = productService.createProduct(request);
        return ApiResponse.ok(response);
    }
}