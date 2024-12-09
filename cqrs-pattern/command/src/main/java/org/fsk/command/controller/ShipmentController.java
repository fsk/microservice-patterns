package org.fsk.command.controller;


import org.fsk.command.commands.shipment.CreateShipmentCommand;
import org.fsk.command.commands.shipment.UpdateShipmentStatusCommand;
import org.fsk.command.responsedto.ShipmentResponse;
import org.fsk.command.entity.enums.ShipmentStatus;
import org.fsk.command.handler.shipment.CreateShipmentCommandHandler;
import org.fsk.command.handler.shipment.UpdateShipmentStatusCommandHandler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/shipments")
@RequiredArgsConstructor
@Tag(name = "Shipment Controller", description = "Shipment management endpoints")
public class ShipmentController {

    private final CreateShipmentCommandHandler createShipmentCommandHandler;
    private final UpdateShipmentStatusCommandHandler updateShipmentStatusCommandHandler;

    @Operation(
        summary = "Create a new shipment",
        description = "Creates a new shipment for a specific order"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Shipment created successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ShipmentResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "id": 1,
                        "orderId": 1,
                        "trackingNumber": "AR123456789",
                        "carrierName": "ARAS",
                        "deliveryAddress": "123 Main St, City, Country",
                        "status": "PREPARING",
                        "estimatedDeliveryDate": "2024-03-20T14:00:00"
                    }
                    """
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<ShipmentResponse> createShipment(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateShipmentCommand.class),
                    examples = @ExampleObject(
                        value = """
                        {
                            "orderId": 1,
                            "recipientCustomerId": 2,
                            "carrierName": "ARAS",
                            "deliveryAddress": "123 Main St, City, Country",
                            "estimatedDeliveryDate": "2024-03-20T14:00:00"
                        }
                        """
                    )
                )
            )
            @RequestBody CreateShipmentCommand command) {
        ShipmentResponse response = createShipmentCommandHandler.handle(command);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Update shipment status",
        description = "Updates the status of an existing shipment"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status updated successfully"),
        @ApiResponse(responseCode = "404", description = "Shipment not found"),
        @ApiResponse(responseCode = "400", description = "Invalid status")
    })
    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateStatus(
            @Parameter(description = "Shipment ID", required = true, example = "1")
            @PathVariable Long id,
            @Parameter(description = "New shipment status", required = true, example = "IN_TRANSIT")
            @RequestParam ShipmentStatus status) {
        updateShipmentStatusCommandHandler.handle(new UpdateShipmentStatusCommand(id, status));
        return ResponseEntity.ok().build();
    }


}
