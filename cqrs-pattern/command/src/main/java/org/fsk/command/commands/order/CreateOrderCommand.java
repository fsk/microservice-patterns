package org.fsk.command.commands.order;

import java.util.List;

public record CreateOrderCommand(
    Long customerId,
    List<OrderItemRequest> items
) {
    
    public record OrderItemRequest(
        Long productId,
        int quantity
    ) {
        
        public OrderItemRequest {
            if (quantity <= 0) {
                throw new IllegalArgumentException("Quantity must be greater than zero");
            }
        }
    }

    
    public CreateOrderCommand {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }
    }
}
