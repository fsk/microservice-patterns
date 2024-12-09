package org.fsk.command.handler.order;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.fsk.command.commands.order.DeleteOrderCommand;
import org.fsk.command.entity.Order;
import org.fsk.command.entity.enums.OrderStatus;
import org.fsk.command.event.order.DeleteOrderDeletedEvent;
import org.fsk.command.handler.EventPublisherService;
import org.fsk.command.repository.OrderRepository;
import lombok.RequiredArgsConstructor;  

@Component
@RequiredArgsConstructor
public class DeleteOrderCommandHandler {
    
    private final OrderRepository orderRepository;
    private final EventPublisherService eventPublisher;
    
    public void handle(DeleteOrderCommand command) {
        Order order = orderRepository.findById(command.orderId())
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + command.orderId()));
        
        validateOrderDeletion(order);
        
        try {
            DeleteOrderDeletedEvent event = DeleteOrderDeletedEvent.create(order);
            orderRepository.delete(order);
            eventPublisher.publishEvent("order.deleted", event);
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete order: " + e.getMessage());
        }
    }
    
    private void validateOrderDeletion(Order order) {
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException(
                "Can only delete orders in PENDING status. Current status: " + order.getStatus()
            );
        }
        
        if (order.getOrderDate().isBefore(LocalDateTime.now().minusDays(1))) {
            throw new RuntimeException(
                "Cannot delete orders older than 24 hours"
            );
        }
    }
}
