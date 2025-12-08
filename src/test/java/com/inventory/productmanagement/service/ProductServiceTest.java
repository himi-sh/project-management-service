package com.inventory.productmanagement.service;

import com.inventory.productmanagement.dto.InventorySummaryDto;
import com.inventory.productmanagement.dto.ProductDto;
import com.inventory.productmanagement.model.Product;
import com.inventory.productmanagement.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void saveProduct_ShouldReturnSavedProduct() {
        ProductDto dto = new ProductDto();
        dto.setName("Laptop");
        dto.setPrice(BigDecimal.valueOf(1000));
        dto.setQuantity(5);

        Product savedProduct = new Product(1L, "Laptop", 5, BigDecimal.valueOf(1000));

        when(productRepository.save(any(Product.class))).thenReturn(savedProduct);

        Product result = productService.saveProduct(dto);

        assertNotNull(result);
        assertEquals("Laptop", result.getName());
        verify(productRepository).save(any(Product.class));
    }

    @Test
    void getSummary_ShouldReturnCorrectSummary() {
        when(productRepository.count()).thenReturn(10L);
        when(productRepository.getTotalQuantity()).thenReturn(50L);
        when(productRepository.getAveragePrice()).thenReturn(BigDecimal.valueOf(250.0));
        when(productRepository.findByQuantity(0)).thenReturn(Collections.emptyList());

        InventorySummaryDto summary = productService.getSummary();

        assertEquals(10L, summary.getTotalProducts());
        assertEquals(50L, summary.getTotalQuantity());
        assertEquals(BigDecimal.valueOf(250.0), summary.getAveragePrice());
        assertTrue(summary.getOutOfStock().isEmpty());
    }
}
