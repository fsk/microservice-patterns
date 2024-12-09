package org.fsk.command.responsedto;


import lombok.Builder;
import lombok.Data;
import org.fsk.command.entity.Order;
import org.fsk.command.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class OrderResponse {
    
    private Long id;
    private String orderNumber;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private Long customerId;
    private String customerName;
    private List<OrderItemResponse> items;

    @Data
    @Builder
    public static class OrderItemResponse {
        private Long productId;
        private String productName;
        private int quantity;
        private BigDecimal unitPrice;
        private BigDecimal totalPrice;
    }

    public static OrderResponse fromEntity(Order order) {
        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> OrderItemResponse.builder()
                        .productId(item.getProduct().getId())
                        .productName(item.getProduct().getName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .totalPrice(item.getUnitPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                        .build())
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .orderDate(order.getOrderDate())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .customerId(order.getCustomer().getId())
                .customerName(order.getCustomer().getName() + " " + order.getCustomer().getSurname())
                .items(itemResponses)
                .build();
    }
}
