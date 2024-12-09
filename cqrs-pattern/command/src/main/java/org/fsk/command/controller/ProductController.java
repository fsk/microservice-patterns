package org.fsk.command.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.fsk.command.commands.product.CreateProductCommand;
import org.fsk.command.commands.product.DeleteProductCommand;
import org.fsk.command.commands.product.UpdateProductCommand;
import org.fsk.command.handler.product.CreateProductCommandHandler;
import org.fsk.command.handler.product.DeleteProductCommandHandler;
import org.fsk.command.handler.product.UpdateProductCommandHandler;
import org.fsk.command.responsedto.ProductResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Product Controller", description = "Product management endpoints")
public class ProductController {

    private final CreateProductCommandHandler createProductCommandHandler;
    private final UpdateProductCommandHandler updateProductCommandHandler;
    private final DeleteProductCommandHandler deleteProductCommandHandler;

    @Operation(
        summary = "Create a new product",
        description = "Creates a new product with the provided details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Product created successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProductResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                        "id": 1,
                        "name": "iPhone 15",
                        "description": "Latest iPhone model",
                        "price": 999.99,
                        "category": "ELECTRONICS",
                        "stockQuantity": 100,
                        "active": true
                    }
                    """
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = CreateProductCommand.class),
                    examples = @ExampleObject(
                        value = """
                        {
                            "name": "iPhone 15",
                            "description": "Latest iPhone model",
                            "price": 999.99,
                            "category": "ELECTRONICS",
                            "stockQuantity": 100
                        }
                        """
                    )
                )
            )
            @RequestBody CreateProductCommand command) {
        return ResponseEntity.ok(createProductCommandHandler.handle(command));
    }

    @Operation(
        summary = "Update an existing product",
        description = "Updates product information based on the provided ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Product updated successfully",
            content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ProductResponse.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Product not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "Product ID", required = true, example = "1")
            @PathVariable Long id,
            @RequestBody UpdateProductCommand command) {
        return ResponseEntity.ok(updateProductCommandHandler.handle(
            new UpdateProductCommand(
                command.productId(),
                command.productName(),
                command.description(),
                command.price(),
                command.stockQuantity(),
                command.category()
            )
        ));
    }

    @Operation(
        summary = "Delete a product",
        description = "Soft deletes a product by setting active status to false"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Product deleted successfully"
        ),
        @ApiResponse(responseCode = "404", description = "Product not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "Product ID", required = true, example = "1")
            @PathVariable Long id) {
        deleteProductCommandHandler.handle(new DeleteProductCommand(id));
        return ResponseEntity.ok().build();
    }
}
