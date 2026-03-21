package com.gridsandcircles.gc_coffee.product.dto.display;

import com.gridsandcircles.gc_coffee.entity.Product;

public record ProductDisplayResponse(
        Long id,
        String name,
        int price
) {
    public static ProductDisplayResponse from(Product product) {
        return new ProductDisplayResponse(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }
}
