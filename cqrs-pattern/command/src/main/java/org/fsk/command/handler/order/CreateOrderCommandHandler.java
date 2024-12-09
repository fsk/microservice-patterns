package org.fsk.command.handler.order;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.fsk.command.commands.order.CreateOrderCommand;
import org.fsk.command.entity.Customer;
import org.fsk.command.entity.Order;
import org.fsk.command.entity.OrderItem;
import org.fsk.command.entity.Product;
import org.fsk.command.entity.enums.OrderStatus;
import org.fsk.command.repository.CustomerRepository;
import org.fsk.command.repository.OrderRepository;
import org.fsk.command.repository.ProductRepository;
import org.fsk.command.responsedto.OrderResponse;

@Component
@RequiredArgsConstructor
public class CreateOrderCommandHandler {
    
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    
    public OrderResponse handle(CreateOrderCommand command) {
        Customer customer = customerRepository.findById(command.customerId())
            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + command.customerId()));
            
        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderNumber(generateOrderNumber());
        order.setStatus(OrderStatus.PENDING);
        
        BigDecimal totalAmount = BigDecimal.ZERO;
        
        for (CreateOrderCommand.OrderItemRequest itemRequest : command.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + itemRequest.productId()));
                
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemRequest.quantity());
            orderItem.setUnitPrice(product.getPrice());
            
            BigDecimal itemTotal = product.getPrice()
                .multiply(BigDecimal.valueOf(itemRequest.quantity()));
            totalAmount = totalAmount.add(itemTotal);
            
            order.getItems().add(orderItem);
        }
        
        order.setTotalAmount(totalAmount);
        
        return OrderResponse.fromEntity(orderRepository.save(order));
    }
    
    private String generateOrderNumber() {
        return "ORD-" + System.currentTimeMillis();
    }
}