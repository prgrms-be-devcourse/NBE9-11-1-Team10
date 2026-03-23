package com.gridsandcircles.gc_coffee.product.service;

import com.gridsandcircles.gc_coffee.entity.Product;
import com.gridsandcircles.gc_coffee.product.dto.ProductCreateRequest;
import com.gridsandcircles.gc_coffee.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Long createProduct(ProductCreateRequest request){
        // 1. 프론트가 준 DTO를 엔티티로 변환
        Product product = request.toEntity();
        // 2. DB에 저장 (save)
        Product savedProduct = productRepository.save(product);
        // 3. 저장되면서 생성된 고유 ID 번호를 반환
        return savedProduct.getId();
    }
}
