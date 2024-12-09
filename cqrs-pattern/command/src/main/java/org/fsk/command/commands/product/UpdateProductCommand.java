package org.fsk.command.commands.product;

import java.math.BigDecimal;

import org.fsk.command.entity.enums.ProductCategory;

public record UpdateProductCommand(
        Long productId,
        String productName,
        String description,
        BigDecimal price,
        int stockQuantity,
        ProductCategory category) {

}
