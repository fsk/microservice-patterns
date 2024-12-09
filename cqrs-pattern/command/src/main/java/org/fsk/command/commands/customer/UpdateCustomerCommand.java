package org.fsk.command.commands.customer;

public record UpdateCustomerCommand(
    Long id,
    String name,
    String surname,
    String email,
    String phone
) {

}
