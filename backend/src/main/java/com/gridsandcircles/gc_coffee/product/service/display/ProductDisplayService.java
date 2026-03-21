package com.gridsandcircles.gc_coffee.product.service.display;

import com.gridsandcircles.gc_coffee.entity.Product;
import com.gridsandcircles.gc_coffee.product.dto.display.ProductDisplayResponse;
import com.gridsandcircles.gc_coffee.product.repository.display.ProductDisplayRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductDisplayService {
    private final ProductDisplayRepository productDisplayRepository;

    public List<ProductDisplayResponse> findProducts(String keyword) {
        List<Product> products;

        if (keyword == null || keyword.isBlank()) {
            products = productDisplayRepository.findAll();
        } else {
            products = productDisplayRepository.findByNameContaining(keyword);
        }

        return products.stream()
                .map(ProductDisplayResponse::from)
                .toList();
    }
}
