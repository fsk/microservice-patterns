package org.fsk.command.responsedto;

import java.time.LocalDateTime;

import org.fsk.command.entity.Shipment;
import org.fsk.command.entity.enums.ShipmentStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipmentResponse {
    
    private Long id;
    private Long orderId;
    private String trackingNumber;
    private String carrierName;
    private String deliveryAddress;
    private ShipmentStatus status;
    private Long recipientCustomerId;
    private String recipientCustomerName;
    private String recipientCustomerEmail;
    private LocalDateTime estimatedDeliveryDate;
    private LocalDateTime actualDeliveryDate;

    public static ShipmentResponse fromEntity(Shipment shipment) {
        return ShipmentResponse.builder()
                .id(shipment.getId())
                .orderId(shipment.getOrder().getId())
                .trackingNumber(shipment.getTrackingNumber())
                .carrierName(shipment.getCarrierName())
                .deliveryAddress(shipment.getDeliveryAddress())
                .status(shipment.getStatus())
                .recipientCustomerId(shipment.getRecipientCustomer().getId())
                .recipientCustomerName(shipment.getRecipientCustomer().getName() + " " + 
                                    shipment.getRecipientCustomer().getSurname())
                .recipientCustomerEmail(shipment.getRecipientCustomer().getEmail())
                .estimatedDeliveryDate(shipment.getEstimatedDeliveryDate())
                .actualDeliveryDate(shipment.getActualDeliveryDate())
                .build();
    }
}
