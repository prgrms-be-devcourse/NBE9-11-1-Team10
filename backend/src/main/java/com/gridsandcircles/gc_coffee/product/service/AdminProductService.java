package com.gridsandcircles.gc_coffee.product.service;

import com.gridsandcircles.gc_coffee.entity.Product;
import com.gridsandcircles.gc_coffee.product.dto.AdminProductResponse;
import com.gridsandcircles.gc_coffee.product.dto.AdminProductUpdateRequest;

@Transactional
public AdminProductResponse updateProduct(Long id, AdminProductUpdateRequest request) {
    // 1. 수정할 상품 조회 (없으면 예외 터뜨림)
    Product product = productRepository.findById(id)
                                       .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다. ID: " + id));

    // 2. 엔티티 값 변경 (JPA 더티 체킹 발동)
    product.update(
            request.getName(),
            request.getPrice(),
            request.getStock()
    );

    // 3. 수정된 결과를 DTO로 변환하여 반환
    return AdminProductResponse.from(product);
}
