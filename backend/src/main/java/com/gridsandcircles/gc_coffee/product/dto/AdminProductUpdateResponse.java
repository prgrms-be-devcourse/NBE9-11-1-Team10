package com.gridsandcircles.gc_coffee.product.dto;

import com.gridsandcircles.gc_coffee.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdminProductUpdateResponse {
    private Long id;
    private String name;
    private int price;
    private int stock;

    public static AdminProductUpdateResponse from(Product product) {
        return new AdminProductUpdateResponse(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }
}