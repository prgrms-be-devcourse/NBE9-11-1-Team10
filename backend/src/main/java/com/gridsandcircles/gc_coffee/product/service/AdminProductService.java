package com.gridsandcircles.gc_coffee.product.service;

import com.gridsandcircles.gc_coffee.entity.Product;
import com.gridsandcircles.gc_coffee.product.dto.AdminProductUpdateRequest;
import com.gridsandcircles.gc_coffee.product.dto.AdminProductUpdateResponse;
import com.gridsandcircles.gc_coffee.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final ProductRepository productRepository;

    @Transactional
    public AdminProductUpdateResponse updateProduct(Long productId, AdminProductUpdateRequest request) {
        // 1. 수정할 상품 조회
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다. ID: " + productId));

        // 2. 엔티티 값 변경 (JPA 더티 체킹)
        product.update(
                request.getName(),
                request.getPrice(),
                request.getStock()
        );

        // 3. 수정된 결과를 DTO로 변환하여 반환
        return AdminProductUpdateResponse.from(product);
    }
}