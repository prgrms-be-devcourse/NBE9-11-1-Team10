package com.gridsandcircles.gc_coffee.product.dto;

import com.gridsandcircles.gc_coffee.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminProductResponse {
    private Long id;
    private String name;
    private int price;
    private int stock;

    public static AdminProductResponse from(Product product) {
        return new AdminProductResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }
}