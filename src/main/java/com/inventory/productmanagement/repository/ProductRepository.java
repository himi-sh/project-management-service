package com.inventory.productmanagement.repository;

import com.inventory.productmanagement.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContainingIgnoreCase(String name);

    @org.springframework.data.jpa.repository.Query("SELECT SUM(p.quantity) FROM Product p")
    Long getTotalQuantity();

    @org.springframework.data.jpa.repository.Query("SELECT AVG(p.price) FROM Product p")
    java.math.BigDecimal getAveragePrice();

    List<Product> findByQuantity(Integer quantity);
}
