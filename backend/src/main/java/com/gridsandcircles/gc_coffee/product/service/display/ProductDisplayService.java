package com.gridsandcircles.gc_coffee.product.service.display;

import com.gridsandcircles.gc_coffee.entity.Product;
import com.gridsandcircles.gc_coffee.product.dto.PageResponse;
import com.gridsandcircles.gc_coffee.product.dto.ProductResponse;
import com.gridsandcircles.gc_coffee.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductDisplayService {
    private final ProductRepository productRepository;

    public PageResponse<ProductResponse> findProducts(String keyword, Pageable pageable) {
        Page<Product> productPage;

        if (keyword == null || keyword.isBlank()) {
            productPage = productRepository.findAll(pageable);
        } else {
            productPage = productRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }

        Page<ProductResponse> responsePage = productPage.map(ProductResponse::from);

        return PageResponse.from(responsePage);
    }
}
