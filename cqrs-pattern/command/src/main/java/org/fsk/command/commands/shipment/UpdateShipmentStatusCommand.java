package org.fsk.command.commands.shipment;

import org.fsk.command.entity.enums.ShipmentStatus;

public record UpdateShipmentStatusCommand(
    Long shipmentId,
    ShipmentStatus newStatus
) {}
