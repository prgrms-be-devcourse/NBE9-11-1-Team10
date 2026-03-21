package com.gridsandcircles.gc_coffee.stock.dto;

public record StockResponseDto(
        Long productId, //상품 아이디
        int availableStock //현재 남은 재고
) {
}
