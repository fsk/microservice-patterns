package org.fsk.command.event.shipment;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class DeleteShipmentDeletedEvent {
    
    private final String eventId;
    private final String eventType;
    private final LocalDateTime occurredOn;

    private final Long shipmentId;
    private final String trackingNumber;
    private final String deleteReason;

    public static DeleteShipmentDeletedEvent create(
            Long shipmentId,
            String trackingNumber,
            String deleteReason) {
        return DeleteShipmentDeletedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("SHIPMENT_DELETED")
                .occurredOn(LocalDateTime.now())
                .shipmentId(shipmentId)
                .trackingNumber(trackingNumber)
                .deleteReason(deleteReason)
                .build();
    }
}
