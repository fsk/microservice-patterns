package org.fsk.command.responsedto;

import org.fsk.command.entity.Customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    
    public static CustomerResponse fromEntity(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .surname(customer.getSurname())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .build();
    }
}
