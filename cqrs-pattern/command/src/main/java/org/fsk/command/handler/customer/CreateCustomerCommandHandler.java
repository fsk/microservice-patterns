package org.fsk.command.handler.customer;

import org.fsk.command.commands.customer.CreateCustomerCommand;
import org.fsk.command.entity.Customer;
import org.fsk.command.event.customer.CreateCustomerCreatedEvent;
import org.fsk.command.handler.EventPublisherService;
import org.fsk.command.repository.CustomerRepository;
import org.fsk.command.responsedto.CustomerResponse;
import org.springframework.stereotype.Component;


import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateCustomerCommandHandler {
    
    private final CustomerRepository customerRepository;
    private final EventPublisherService eventPublisher;
    
    public CustomerResponse handle(CreateCustomerCommand command) {
        
        validateCreateCommand(command);
        
        
        Customer customer = Customer.builder()
                .name(command.name())
                .surname(command.surname())
                .email(command.email())
                .phone(command.phone())
                .build();
        
        
        Customer savedCustomer = customerRepository.save(customer);
        
        
        CreateCustomerCreatedEvent event = CreateCustomerCreatedEvent.create(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.getSurname(),
                savedCustomer.getEmail(),
                savedCustomer.getPhone()
        );
        eventPublisher.publishEvent("customer.routing.key", event);
        
        
        return CustomerResponse.fromEntity(savedCustomer);
    }
    
    private void validateCreateCommand(CreateCustomerCommand command) {
        if (customerRepository.existsByEmail(command.email())) {
            throw new RuntimeException("Email already exists: " + command.email());
        }
    }
}
