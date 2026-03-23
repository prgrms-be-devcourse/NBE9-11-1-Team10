package com.gridsandcircles.gc_coffee.product.dto;

import com.gridsandcircles.gc_coffee.entity.Product;

public record ProductCreateRequest(
        String name,
        int price,
        int stock
) {
    public Product toEntity(){
        return new Product(this.name, this.price, this.stock);
    }
}