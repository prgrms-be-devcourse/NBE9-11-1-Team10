package com.gridsandcircles.gc_coffee.product.dto;

import com.gridsandcircles.gc_coffee.entity.Product;

public record ProductCreateResponse(Long productId, String name){
    public static com.gridsandcircles.gc_coffee.product.dto.ProductCreateResponse from(Product product){
        return new com.gridsandcircles.gc_coffee.product.dto.ProductCreateResponse(product.getId(), product.getName());
    }
}
