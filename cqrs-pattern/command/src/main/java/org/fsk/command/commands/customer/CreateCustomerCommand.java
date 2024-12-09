package org.fsk.command.commands.customer;

public record CreateCustomerCommand(
    String name,
    String surname,
    String email,
    String phone
) {
} 