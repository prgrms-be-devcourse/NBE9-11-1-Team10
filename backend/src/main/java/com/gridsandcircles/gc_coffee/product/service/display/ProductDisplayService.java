package com.gridsandcircles.gc_coffee.product.service.display;

import com.gridsandcircles.gc_coffee.entity.Product;
import com.gridsandcircles.gc_coffee.product.dto.display.PageResponse;
import com.gridsandcircles.gc_coffee.product.dto.display.ProductDisplayResponse;
import com.gridsandcircles.gc_coffee.product.repository.display.ProductDisplayRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductDisplayService {
    private final ProductDisplayRepository productDisplayRepository;

    public PageResponse<ProductDisplayResponse> findProducts(String keyword, Pageable pageable) {
        Page<Product> productPage;

        if (keyword == null || keyword.isBlank()) {
            productPage = productDisplayRepository.findAll(pageable);
        } else {
            productPage = productDisplayRepository.findByNameContainingIgnoreCase(keyword, pageable);
        }

        Page<ProductDisplayResponse> responsePage = productPage.map(ProductDisplayResponse::from);

        return PageResponse.from(responsePage);
    }
}
