package com.gridsandcircles.gc_coffee.product.repository.display;

import com.gridsandcircles.gc_coffee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDisplayRepository extends JpaRepository<Product, Long> {
}
