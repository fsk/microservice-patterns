package org.fsk.command.commands.product;

import org.fsk.command.entity.enums.ProductCategory;

import java.math.BigDecimal;

public record CreateProductCommand(
        String productName,
        String description,
        BigDecimal price,
        int stockQuantity,
        ProductCategory category
) {
}
