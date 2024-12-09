package org.fsk.command.event.customer;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateCustomerCreatedEvent {

    private final String eventId;
    private final String eventType;
    private final LocalDateTime occuredOn;

    private final Long customerId;
    private final String name;
    private final String surname;
    private final String email;
    private final String phone;
    
    public static CreateCustomerCreatedEvent create(Long customerId, String name, 
            String surname, String email, String phone) {
        return CreateCustomerCreatedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("CUSTOMER_CREATED")
                .occuredOn(LocalDateTime.now())
                .customerId(customerId)
                .name(name)
                .surname(surname)
                .email(email)
                .phone(phone)
                .build();
    }
}
