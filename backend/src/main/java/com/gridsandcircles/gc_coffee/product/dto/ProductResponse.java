package com.gridsandcircles.gc_coffee.product.dto;

import com.gridsandcircles.gc_coffee.entity.Product;

public record ProductResponse(
        Long id,
        String name,
        int price,
        int stock
) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }
}
