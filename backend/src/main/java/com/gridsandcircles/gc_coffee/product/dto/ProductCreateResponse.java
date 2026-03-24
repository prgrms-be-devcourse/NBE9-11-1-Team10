package com.gridsandcircles.gc_coffee.product.dto;

import com.gridsandcircles.gc_coffee.entity.Product;

public record ProductCreateResponse(Long productId, String name, int price, int stock){
    public static ProductCreateResponse from(Product product){
        return new ProductCreateResponse(product.getId(), product.getName(), product.getPrice(), product.getStock());
    }
}
