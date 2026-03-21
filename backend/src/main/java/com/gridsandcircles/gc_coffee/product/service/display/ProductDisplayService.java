package com.gridsandcircles.gc_coffee.product.service.display;

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

    public List<ProductDisplayResponse> findAllProducts() {
        return productDisplayRepository.findAll()
                .stream()
                .map(ProductDisplayResponse::from)
                .toList();
    }
}
