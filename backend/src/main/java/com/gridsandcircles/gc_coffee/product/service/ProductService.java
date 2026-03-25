package com.gridsandcircles.gc_coffee.product.service;

import com.gridsandcircles.gc_coffee.entity.Product;
import com.gridsandcircles.gc_coffee.global.exception.BusinessException;
import com.gridsandcircles.gc_coffee.global.exception.ErrorCode;
import com.gridsandcircles.gc_coffee.product.dto.AdminProductUpdateRequest;
import com.gridsandcircles.gc_coffee.product.dto.AdminProductUpdateResponse;
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


    @Transactional
    public AdminProductUpdateResponse updateProduct(Long productId, AdminProductUpdateRequest request) {
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new IllegalArgumentException("해당 상품을 찾을 수 없습니다. ID: " + productId));

        product.update(
                request.getName(),
                request.getPrice(),
                request.getStock()
        );

        return AdminProductUpdateResponse.from(product);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                                           .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        productRepository.delete(product);
    }
}
