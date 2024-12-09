package org.fsk.command.handler.customer;

import org.fsk.command.commands.customer.UpdateCustomerCommand;
import org.fsk.command.entity.Customer;
import org.fsk.command.event.customer.UpdateCustomerUpdatedEvent;
import org.fsk.command.handler.EventPublisherService;
import org.fsk.command.repository.CustomerRepository;
import org.fsk.command.responsedto.CustomerResponse;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UpdateCustomerCommandHandler {

    private final CustomerRepository customerRepository;
    private final EventPublisherService eventPublisher;

    public CustomerResponse handle(UpdateCustomerCommand command) {

        Customer existingCustomer = customerRepository
        .findById(command.id())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        try {

            existingCustomer.setName(command.name());
            existingCustomer.setSurname(command.surname());
            existingCustomer.setEmail(command.email());
            existingCustomer.setPhone(command.phone());

            Customer updatedCustomer = customerRepository.save(existingCustomer);

            UpdateCustomerUpdatedEvent event = UpdateCustomerUpdatedEvent.create(
                updatedCustomer.getId(), 
                updatedCustomer.getName(), 
                updatedCustomer.getSurname(), 
                updatedCustomer.getEmail(), 
                updatedCustomer.getPhone());
                
            eventPublisher.publishEvent("customer.routing.key", event);

            return CustomerResponse.fromEntity(updatedCustomer);

        }catch(Exception e) {
            throw new RuntimeException("Error updating customer", e);
        }     

        
    }

}
