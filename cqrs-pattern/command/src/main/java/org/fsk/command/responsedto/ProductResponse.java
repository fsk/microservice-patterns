package org.fsk.command.responsedto;

import lombok.Builder;
import lombok.Data;
import org.fsk.command.entity.Product;
import org.fsk.command.entity.enums.ProductCategory;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private ProductCategory category;
    private boolean active;

    public static ProductResponse fromEntity(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .category(product.getCategory())
                .active(product.isActive())
                .build();
    }
}