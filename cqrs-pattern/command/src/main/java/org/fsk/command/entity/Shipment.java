package org.fsk.command.entity;

import java.time.LocalDateTime;

import org.fsk.command.entity.enums.ShipmentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Entity
@Table(name = "shipment")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shipment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    private String trackingNumber;
    private String carrierName;
    private String deliveryAddress;
    
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    @ManyToOne
    @JoinColumn(name = "recipient_customer_id")
    private Customer recipientCustomer;

    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime actualDeliveryDate;

}
