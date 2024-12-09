package org.fsk.command.event.customer;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DeleteCustomerDeletedEvent {

    private final String eventId;
    private final String eventType;
    private final LocalDateTime occuredOn;

    private final Long customerId;

    public static DeleteCustomerDeletedEvent create(Long customerId) {
        return DeleteCustomerDeletedEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("CUSTOMER_DELETED")
                .occuredOn(LocalDateTime.now())
                .customerId(customerId)
                .build();
    }
}
