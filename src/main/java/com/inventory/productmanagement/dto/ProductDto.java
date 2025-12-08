package com.inventory.productmanagement.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductDto {
    private Long id;
    private String name;
    private Integer quantity;
    private BigDecimal price;
}
