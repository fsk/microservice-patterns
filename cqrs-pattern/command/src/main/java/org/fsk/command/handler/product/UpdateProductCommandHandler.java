package org.fsk.command.handler.product;

import org.fsk.command.commands.product.UpdateProductCommand;
import org.fsk.command.entity.Product;
import org.fsk.command.event.product.UpdateProductCreatedEvent;
import org.fsk.command.handler.EventPublisherService;
import org.fsk.command.repository.ProductRepository;
import org.fsk.command.responsedto.ProductResponse;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateProductCommandHandler {

    private final ProductRepository productRepository;
    private final EventPublisherService eventPublisher;

    public ProductResponse handle(UpdateProductCommand command) {

        Product product = productRepository.findById(command.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(command.productName());
        product.setDescription(command.description());
        product.setPrice(command.price());
        product.setStockQuantity(command.stockQuantity());
        product.setCategory(command.category());

        Product updatedProduct = productRepository.save(product);

        UpdateProductCreatedEvent event = UpdateProductCreatedEvent.create(updatedProduct.getId(), updatedProduct.getName(), updatedProduct.getDescription(), updatedProduct.getPrice(), updatedProduct.getStockQuantity(), updatedProduct.getCategory());
        eventPublisher.publishEvent("product.routing.key", event);

        return ProductResponse.fromEntity(updatedProduct);

    }
}
