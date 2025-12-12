package com.inventory.productmanagement.service;

import com.inventory.productmanagement.dto.InventorySummaryDto;
import com.inventory.productmanagement.dto.ProductDto;
import com.inventory.productmanagement.exception.ProductNotFoundException;
import com.inventory.productmanagement.model.Product;
import com.inventory.productmanagement.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product saveProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setQuantity(productDto.getQuantity());
        product.setPrice(productDto.getPrice());
        return productRepository.save(product);
    }

    public Page<Product> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    public Product updateQuantity(Long id, Integer quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        product.setQuantity(quantity);
        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }

    public InventorySummaryDto getSummary() {
        long totalProducts = productRepository.count();
        Long totalQuantity = productRepository.getTotalQuantity();
        BigDecimal averagePrice = productRepository.getAveragePrice();
        List<Product> outOfStockProducts = productRepository.findByQuantity(0);

        List<InventorySummaryDto.ProductSummaryDto> outOfStockDtos = outOfStockProducts.stream()
                .map(p -> InventorySummaryDto.ProductSummaryDto.builder()
                        .id(p.getId())
                        .name(p.getName())
                        .build())
                .collect(Collectors.toList());

        return InventorySummaryDto.builder()
                .totalProducts(totalProducts)
                .totalQuantity(totalQuantity != null ? totalQuantity : 0)
                .averagePrice(averagePrice != null ? averagePrice : BigDecimal.ZERO)
                .outOfStock(outOfStockDtos)
                .build();
    }
}
