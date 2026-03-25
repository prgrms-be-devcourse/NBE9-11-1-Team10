package com.gridsandcircles.gc_coffee.product.controller;

import com.gridsandcircles.gc_coffee.global.dto.ApiResponse;
import com.gridsandcircles.gc_coffee.product.dto.AdminProductUpdateRequest;
import com.gridsandcircles.gc_coffee.product.dto.AdminProductUpdateResponse;
import com.gridsandcircles.gc_coffee.product.dto.ProductCreateRequest;
import com.gridsandcircles.gc_coffee.product.dto.ProductCreateResponse;
import com.gridsandcircles.gc_coffee.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{productId}")
    public ApiResponse<AdminProductUpdateResponse> updateProduct(
            @PathVariable Long productId,
            @RequestBody AdminProductUpdateRequest request) {

        AdminProductUpdateResponse response = productService.updateProduct(productId, request);
        return ApiResponse.ok(response);
    }

    @DeleteMapping("/{productId}")
    public ApiResponse<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);

        return ApiResponse.ok();
    }
}