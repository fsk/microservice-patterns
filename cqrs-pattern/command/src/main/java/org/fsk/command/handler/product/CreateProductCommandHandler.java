package org.fsk.command.handler.product;

import org.fsk.command.commands.product.CreateProductCommand;
import org.fsk.command.entity.Product;
import org.fsk.command.event.product.CreateProductCreatedEvent;
import org.fsk.command.handler.EventPublisherService;
import org.fsk.command.repository.ProductRepository;
import org.fsk.command.responsedto.ProductResponse;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateProductCommandHandler {

    private final ProductRepository productRepository;
    private final EventPublisherService eventPublisher;

    public ProductResponse handle(CreateProductCommand command) {

        Product product = Product.builder()
                .name(command.productName())
                .description(command.description())
                .price(command.price())
                .stockQuantity(command.stockQuantity())
                .category(command.category())
                .build();

        Product savedProduct = productRepository.save(product);

        CreateProductCreatedEvent event = CreateProductCreatedEvent.create(
                savedProduct.getId(), 
                savedProduct.getName(), 
                savedProduct.getDescription(), 
                savedProduct.getPrice(), 
                savedProduct.getStockQuantity(), 
                savedProduct.getCategory());
        eventPublisher.publishEvent("product.routing.key", event);

        return ProductResponse.fromEntity(savedProduct);

    }

}
