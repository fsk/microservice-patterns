package org.fsk.command.handler.shipment;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;
import org.fsk.command.repository.ShipmentRepository;
import org.fsk.command.responsedto.ShipmentResponse;
import org.fsk.command.commands.shipment.UpdateShipmentStatusCommand;
import org.fsk.command.event.shipment.UpdateShipmentUpdatedEvent;
import org.fsk.command.handler.EventPublisherService;
import org.fsk.command.entity.Shipment;
import org.fsk.command.entity.enums.ShipmentStatus;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateShipmentStatusCommandHandler {
    
    private final ShipmentRepository shipmentRepository;
    private final EventPublisherService eventPublisher;
    
    public ShipmentResponse handle(UpdateShipmentStatusCommand command) {
        
        Shipment shipment = shipmentRepository.findById(command.shipmentId())
            .orElseThrow(() -> new RuntimeException("Shipment not found with id: " + command.shipmentId()));
        
        
        shipment.setStatus(command.newStatus());
        
        
        if (command.newStatus() == ShipmentStatus.DELIVERED) {
            shipment.setActualDeliveryDate(LocalDateTime.now());
        }
        
        
        Shipment updatedShipment = shipmentRepository.save(shipment);
        
        
        UpdateShipmentUpdatedEvent event = UpdateShipmentUpdatedEvent.create(
                updatedShipment.getId(),
                updatedShipment.getTrackingNumber(),
                updatedShipment.getCarrierName(),
                updatedShipment.getDeliveryAddress(),
                updatedShipment.getStatus(),
                updatedShipment.getEstimatedDeliveryDate(),
                updatedShipment.getActualDeliveryDate()
        );
        eventPublisher.publishEvent("shipment.status.updated", event);
        
        return ShipmentResponse.fromEntity(updatedShipment);
    }
}