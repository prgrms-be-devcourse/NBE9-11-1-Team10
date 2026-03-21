package com.gridsandcircles.gc_coffee.product.repository;

import com.gridsandcircles.gc_coffee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}
