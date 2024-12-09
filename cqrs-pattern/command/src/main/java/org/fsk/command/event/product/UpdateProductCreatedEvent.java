package org.fsk.command.event.product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.fsk.command.entity.enums.ProductCategory;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder    
public class UpdateProductCreatedEvent {

    private final String eventId;
    private final String eventType;
    private final LocalDateTime occuredOn;

    private final Long productId;
    private final String productName;
    private final String description;
    private final BigDecimal price;
    private final int stockQuantity;
    private final ProductCategory category; 

    public static UpdateProductCreatedEvent create(Long productId, String productName, String description, BigDecimal price, int stockQuantity, ProductCategory category) {
        return UpdateProductCreatedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("PRODUCT_UPDATED")
                .occuredOn(LocalDateTime.now())
                .productId(productId)
                .productName(productName)
                .description(description)
                .price(price)
                .stockQuantity(stockQuantity)
                .category(category)
                .build();
    }

}
