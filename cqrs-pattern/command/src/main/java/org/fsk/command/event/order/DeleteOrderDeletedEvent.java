package org.fsk.command.event.order;

import lombok.Builder;
import lombok.Getter;
import org.fsk.command.entity.Order;
import org.fsk.command.entity.enums.OrderStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class DeleteOrderDeletedEvent {
    
    private final String eventId;
    private final String eventType;
    private final LocalDateTime occurredOn;

    private final Long orderId;
    private final String orderNumber;
    private final Long customerId;
    private final OrderStatus previousStatus;
    private final LocalDateTime deletedAt;

    public static DeleteOrderDeletedEvent create(Order order) {
        return DeleteOrderDeletedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("ORDER_DELETED")
                .occurredOn(LocalDateTime.now())
                .orderId(order.getId())
                .orderNumber(order.getOrderNumber())
                .customerId(order.getCustomer().getId())
                .previousStatus(order.getStatus())
                .deletedAt(LocalDateTime.now())
                .build();
    }
}
