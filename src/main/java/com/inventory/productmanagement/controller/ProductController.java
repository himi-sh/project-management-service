package com.inventory.productmanagement.controller;

import com.inventory.productmanagement.dto.InventorySummaryDto;
import com.inventory.productmanagement.dto.ProductDto;
import com.inventory.productmanagement.model.Product;
import com.inventory.productmanagement.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product")
    public ResponseEntity<Product> createProduct(@Valid @RequestBody ProductDto productDto) {
        return new ResponseEntity<>(productService.saveProduct(productDto), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "List all products with pagination")
    public ResponseEntity<Page<Product>> getAllProducts(Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Search products by name")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchProducts(name));
    }

    @PutMapping("/{id}/quantity")
    @Operation(summary = "Update product quantity")
    public ResponseEntity<Product> updateQuantity(@PathVariable Long id, @RequestParam Integer quantity) { // Taking
                                                                                                           // quantity
                                                                                                           // as param
                                                                                                           // or body?
                                                                                                           // Requirement
                                                                                                           // says PUT
                                                                                                           // /products/{id}/quantity.
                                                                                                           // Usually
                                                                                                           // implies
                                                                                                           // body or
                                                                                                           // path, but
                                                                                                           // param is
                                                                                                           // easiest.
                                                                                                           // Let's
                                                                                                           // assume
                                                                                                           // request
                                                                                                           // param
                                                                                                           // "quantity=10"
                                                                                                           // or body
                                                                                                           // value.
                                                                                                           // I'll
                                                                                                           // support
                                                                                                           // param for
                                                                                                           // simplicity
                                                                                                           // or body if
                                                                                                           // strictly
                                                                                                           // REST.
                                                                                                           // Let's use
                                                                                                           // RequestParam.
        // Wait, "PUT /products/{id}/quantity". If I post a raw integer body, I need to
        // handle it.
        // Let's assume query param ?quantity=... is acceptable or body.
        // Given complexity, let's use @RequestParam for simplicity unless specified.
        return ResponseEntity.ok(productService.updateQuantity(id, quantity));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/summary")
    @Operation(summary = "Get inventory statistics")
    public ResponseEntity<InventorySummaryDto> getSummary() {
        return ResponseEntity.ok(productService.getSummary());
    }
}
