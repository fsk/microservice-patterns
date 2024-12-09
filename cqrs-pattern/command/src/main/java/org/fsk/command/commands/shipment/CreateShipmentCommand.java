package org.fsk.command.commands.shipment;

import java.time.LocalDateTime;

public record CreateShipmentCommand(
    Long orderId,
    Long recipientCustomerId,
    String carrierName,
    String deliveryAddress,
    LocalDateTime estimatedDeliveryDate
) {}
