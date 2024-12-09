package org.fsk.command.event.shipment;

import lombok.Builder;
import lombok.Getter;
import org.fsk.command.entity.enums.ShipmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class UpdateShipmentUpdatedEvent {
    
    private final String eventId;
    private final String eventType;
    private final LocalDateTime occurredOn;

    private final Long shipmentId;
    private final String trackingNumber;
    private final String carrierName;
    private final String deliveryAddress;
    private final ShipmentStatus status;
    private final LocalDateTime estimatedDeliveryDate;
    private final LocalDateTime actualDeliveryDate;

    public static UpdateShipmentUpdatedEvent create(
            Long shipmentId,
            String trackingNumber,
            String carrierName,
            String deliveryAddress,
            ShipmentStatus status,
            LocalDateTime estimatedDeliveryDate,
            LocalDateTime actualDeliveryDate) {
        return UpdateShipmentUpdatedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("SHIPMENT_UPDATED")
                .occurredOn(LocalDateTime.now())
                .shipmentId(shipmentId)
                .trackingNumber(trackingNumber)
                .carrierName(carrierName)
                .deliveryAddress(deliveryAddress)
                .status(status)
                .estimatedDeliveryDate(estimatedDeliveryDate)
                .actualDeliveryDate(actualDeliveryDate)
                .build();
    }
}