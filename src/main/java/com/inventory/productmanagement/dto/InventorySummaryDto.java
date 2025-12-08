package com.inventory.productmanagement.dto;

import lombok.Data;
import lombok.Builder;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class InventorySummaryDto {
    private long totalProducts;
    private long totalQuantity;
    private BigDecimal averagePrice;
    private List<ProductSummaryDto> outOfStock;

    @Data
    @Builder
    public static class ProductSummaryDto {
        private Long id;
        private String name;
    }
}
