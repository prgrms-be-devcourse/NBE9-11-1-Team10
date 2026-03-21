package com.gridsandcircles.gc_coffee.stock.repository;

import com.gridsandcircles.gc_coffee.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStockRepository extends JpaRepository<Product,Long> {
}
