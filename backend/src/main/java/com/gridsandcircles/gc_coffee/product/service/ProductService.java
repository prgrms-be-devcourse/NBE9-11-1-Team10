package com.gridsandcircles.gc_coffee.product.service;

import com.gridsandcircles.gc_coffee.entity.Product;
import com.gridsandcircles.gc_coffee.product.dto.ProductCreateRequest;
import com.gridsandcircles.gc_coffee.product.dto.ProductCreateResponse;
import com.gridsandcircles.gc_coffee.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductCreateResponse createProduct(ProductCreateRequest request){
        Product product = request.toEntity();
        Product savedProduct = productRepository.save(product);
        return ProductCreateResponse.from(savedProduct); // 수정한 응답 DTO 적용
    }
}
