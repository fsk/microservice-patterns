package org.fsk.command.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.fsk.command.commands.customer.CreateCustomerCommand;
import org.fsk.command.commands.customer.DeleteCustomerCommand;
import org.fsk.command.commands.customer.UpdateCustomerCommand;
import org.fsk.command.handler.customer.CreateCustomerCommandHandler;
import org.fsk.command.handler.customer.DeleteCustomerCommandHandler;
import org.fsk.command.handler.customer.UpdateCustomerCommandHandler;
import org.fsk.command.responsedto.CustomerResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customer Controller", description = "Customer management endpoints")
public class CustomerController {

    private final CreateCustomerCommandHandler createCustomerHandler;
    private final UpdateCustomerCommandHandler updateCustomerHandler;
    private final DeleteCustomerCommandHandler deleteCustomerHandler;

    @Operation(
        summary = "Create a new customer",
        description = "Creates a new customer with the provided details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Customer created successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = CustomerResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "id": 1,
                        "name": "John",
                        "surname": "Doe",
                        "email": "john.doe@example.com",
                        "phone": "5551234567"
                    }
                    """
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "Customer already exists with this email")
    })
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateCustomerCommand.class),
                    examples = @ExampleObject(
                        value = """
                        {
                            "name": "John",
                            "surname": "Doe",
                            "email": "john.doe@example.com",
                            "phone": "5551234567"
                        }
                        """
                    )
                )
            )
            @RequestBody CreateCustomerCommand command) {
        return ResponseEntity.ok(createCustomerHandler.handle(command));
    }

    @Operation(
        summary = "Update an existing customer",
        description = "Updates customer information based on the provided ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Customer updated successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = CustomerResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "id": 1,
                        "name": "John",
                        "surname": "Smith",
                        "email": "john.smith@example.com",
                        "phone": "5559876543"
                    }
                    """
                )
            )
        ),
        @ApiResponse(responseCode = "404", description = "Customer not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "409", description = "Email already in use")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @Parameter(
                description = "Customer ID",
                required = true,
                example = "1"
            )
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = UpdateCustomerCommand.class),
                    examples = @ExampleObject(
                        value = """
                        {
                            "name": "John",
                            "surname": "Smith",
                            "email": "john.smith@example.com",
                            "phone": "5559876543"
                        }
                        """
                    )
                )
            )
            @RequestBody UpdateCustomerCommand command) {
        return ResponseEntity.ok(updateCustomerHandler.handle(
            new UpdateCustomerCommand(
                id,
                command.name(),
                command.surname(),
                command.email(),
                command.phone()
            )
        ));
    }

    @Operation(
        summary = "Delete a customer",
        description = "Soft deletes the customer with the specified ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "204",
            description = "Customer deleted successfully"
        ),
        @ApiResponse(responseCode = "404", description = "Customer not found"),
        @ApiResponse(responseCode = "400", description = "Customer cannot be deleted")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(
                description = "Customer ID",
                required = true,
                example = "1"
            )
            @PathVariable Long id) {
        deleteCustomerHandler.handle(new DeleteCustomerCommand(id));
        return ResponseEntity.noContent().build();
    }
}
