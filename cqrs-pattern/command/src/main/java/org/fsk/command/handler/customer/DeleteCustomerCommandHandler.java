package org.fsk.command.handler.customer;

import org.fsk.command.commands.customer.DeleteCustomerCommand;
import org.fsk.command.entity.Customer;
import org.fsk.command.event.customer.DeleteCustomerDeletedEvent;
import org.fsk.command.handler.EventPublisherService;
import org.fsk.command.repository.CustomerRepository;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeleteCustomerCommandHandler {

    private final CustomerRepository customerRepository;
    private final EventPublisherService eventPublisher;

    public void handle(DeleteCustomerCommand command) {

        Customer customer = customerRepository.findById(command.id())
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        customerRepository.delete(customer);

        DeleteCustomerDeletedEvent event = DeleteCustomerDeletedEvent.create(customer.getId());
        eventPublisher.publishEvent("customer.routing.key", event);
    }

}
