package org.fsk.command.repository;

import org.fsk.command.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long>{

    boolean existsByEmail(String email);
    
}
