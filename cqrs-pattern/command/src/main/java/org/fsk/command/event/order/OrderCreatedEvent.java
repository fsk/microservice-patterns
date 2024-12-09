package org.fsk.command.event.order;

import lombok.Getter;
import lombok.Builder;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.fsk.command.entity.Order;

import org.fsk.command.entity.enums.OrderStatus;

@Getter
@Builder
public class OrderCreatedEvent {
    
    private final String eventId;
    private final String eventType;
    private final LocalDateTime occurredOn;

    private final Long orderId;
    private final String orderNumber;
    private final Long customerId;
    private final BigDecimal totalAmount;
    private final OrderStatus status;
    private final List<OrderItemDto> items;

    @Getter
    @Builder
    public static class OrderItemDto {
        private final Long productId;
        private final int quantity;
        private final BigDecimal unitPrice;
        private final BigDecimal totalPrice;
    }

    public static OrderCreatedEvent create(Order order) {
        List<OrderItemDto> itemDtos = order.getItems().stream()
                .map(item -> OrderItemDto.builder()
                        .productId(item.getProduct().getId())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .totalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .build())
                .collect(Collectors.toList());

        return OrderCreatedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("ORDER_CREATED")
                .occurredOn(LocalDateTime.now())
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerId(order.getCustomer().getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .items(itemDtos)
                .build();
    }
}
