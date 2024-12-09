package org.fsk.command.handler.product;

import org.fsk.command.commands.product.DeleteProductCommand;
import org.fsk.command.entity.Product;
import org.fsk.command.event.product.DeleteProductCreatedEvent;
import org.fsk.command.handler.EventPublisherService;
import org.fsk.command.repository.ProductRepository;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteProductCommandHandler {

    private final ProductRepository productRepository;
    private final EventPublisherService eventPublisher;

    public void handle(DeleteProductCommand command) {
        Product product = productRepository.findById(command.productId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productRepository.delete(product);

        DeleteProductCreatedEvent event = DeleteProductCreatedEvent.create(
                product.getId(), 
                product.getName(), 
                product.getDescription(), 
                product.getPrice(), 
                product.getStockQuantity(), 
                product.getCategory());
                
        eventPublisher.publishEvent("product.routing.key", event);


    }

}
