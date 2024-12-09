package org.fsk.command.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.fsk.command.responsedto.OrderResponse;
import org.fsk.command.commands.order.CreateOrderCommand;
import org.fsk.command.commands.order.DeleteOrderCommand;
import org.fsk.command.handler.order.CreateOrderCommandHandler;
import org.fsk.command.handler.order.DeleteOrderCommandHandler;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Order Controller", description = "Order management endpoints")
public class OrderController {

    private final CreateOrderCommandHandler createOrderCommandHandler;
    private final DeleteOrderCommandHandler deleteOrderCommandHandler;

    @Operation(
        summary = "Create a new order",
        description = "Creates a new order with the provided details including order items"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Order created successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = OrderResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "id": 1,
                        "customerId": 1,
                        "orderDate": "2024-03-15T10:30:00",
                        "status": "PENDING",
                        "items": [
                            {
                                "productId": 1,
                                "productName": "iPhone 15",
                                "quantity": 2,
                                "unitPrice": 999.99,
                                "totalPrice": 1999.98
                            }
                        ]
                    }
                    """
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "409", description = "Insufficient stock")
    })
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateOrderCommand.class),
                    examples = @ExampleObject(
                        value = """
                        {
                            "customerId": 1,
                            "items": [
                                {
                                    "productId": 1,
                                    "quantity": 2
                                }
                            ]
                        }
                        """
                    )
                )
            )
            @RequestBody CreateOrderCommand command) {
        OrderResponse response = createOrderCommandHandler.handle(command);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Cancel an order",
        description = "Cancels an existing order by ID if it hasn't been shipped"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Order cancelled successfully"
        ),
        @ApiResponse(responseCode = "404", description = "Order not found"),
        @ApiResponse(responseCode = "400", description = "Order cannot be cancelled (already shipped/delivered)")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelOrder(
            @Parameter(
                description = "Order ID",
                required = true,
                example = "1"
            )
            @PathVariable Long id) {
        deleteOrderCommandHandler.handle(new DeleteOrderCommand(id));
        return ResponseEntity.ok().build();
    }

}
