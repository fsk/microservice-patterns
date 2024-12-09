package org.fsk.command.handler.shipment;


import org.fsk.command.commands.shipment.CreateShipmentCommand;
import org.fsk.command.entity.Customer;
import org.fsk.command.entity.Order;
import org.fsk.command.entity.Shipment;
import org.fsk.command.entity.enums.ShipmentStatus;
import org.fsk.command.event.shipment.CreateShipmentCreatedEvent;
import org.fsk.command.handler.EventPublisherService;
import org.fsk.command.repository.CustomerRepository;
import org.fsk.command.repository.OrderRepository;
import org.fsk.command.repository.ShipmentRepository;
import org.fsk.command.responsedto.ShipmentResponse;
import org.fsk.command.util.TrackingNumberGenerator;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateShipmentCommandHandler {
    
    private final ShipmentRepository shipmentRepository;
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final EventPublisherService eventPublisher;
    private final TrackingNumberGenerator trackingNumberGenerator;

    
    public ShipmentResponse handle(CreateShipmentCommand command) {
        
        Order order = orderRepository.findById(command.orderId())
            .orElseThrow(() -> new RuntimeException("Order not found with id: " + command.orderId()));
            
        Customer recipient = customerRepository.findById(command.recipientCustomerId())
            .orElseThrow(() -> new RuntimeException("Customer not found with id: " + command.recipientCustomerId()));
        
        
        Shipment shipment = Shipment.builder()
                .order(order)
                .recipientCustomer(recipient)
                .carrierName(command.carrierName())
                .deliveryAddress(command.deliveryAddress())
                .status(ShipmentStatus.PREPARING) 
                .trackingNumber(trackingNumberGenerator.generate(command.carrierName()))
                .estimatedDeliveryDate(command.estimatedDeliveryDate())
                .build();
        
        Shipment savedShipment = shipmentRepository.save(shipment);
        
        
        CreateShipmentCreatedEvent event = CreateShipmentCreatedEvent.create(
                savedShipment.getId(),
                savedShipment.getOrder().getId(),
                savedShipment.getRecipientCustomer().getId(),
                savedShipment.getTrackingNumber(),
                savedShipment.getCarrierName(),
                savedShipment.getDeliveryAddress(),
                savedShipment.getStatus(),
                savedShipment.getEstimatedDeliveryDate()
        );
        eventPublisher.publishEvent("shipment.created", event);
        
        return ShipmentResponse.fromEntity(savedShipment);
    }
    
}
