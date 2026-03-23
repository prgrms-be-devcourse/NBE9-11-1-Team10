package com.gridsandcircles.gc_coffee.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AdminProductUpdateRequest {
    private String name;
    private int price;
    private int stock;
    private boolean isDisplayed;
}