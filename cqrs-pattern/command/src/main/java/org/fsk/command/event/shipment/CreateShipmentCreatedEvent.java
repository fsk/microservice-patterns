package org.fsk.command.event.shipment;


import lombok.Builder;
import lombok.Getter;
import org.fsk.command.entity.enums.ShipmentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CreateShipmentCreatedEvent {
    
    private final String eventId;
    private final String eventType;
    private final LocalDateTime occurredOn;

    private final Long shipmentId;
    private final Long orderId;
    private final Long recipientCustomerId;
    private final String trackingNumber;
    private final String carrierName;
    private final String deliveryAddress;
    private final ShipmentStatus status;
    private final LocalDateTime estimatedDeliveryDate;

    public static CreateShipmentCreatedEvent create(
            Long shipmentId,
            Long orderId,
            Long recipientCustomerId,
            String trackingNumber,
            String carrierName,
            String deliveryAddress,
            ShipmentStatus status,
            LocalDateTime estimatedDeliveryDate) {
        return CreateShipmentCreatedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("SHIPMENT_CREATED")
                .occurredOn(LocalDateTime.now())
                .shipmentId(shipmentId)
                .orderId(orderId)
                .recipientCustomerId(recipientCustomerId)
                .trackingNumber(trackingNumber)
                .carrierName(carrierName)
                .deliveryAddress(deliveryAddress)
                .status(status)
                .estimatedDeliveryDate(estimatedDeliveryDate)
                .build();
    }
}
