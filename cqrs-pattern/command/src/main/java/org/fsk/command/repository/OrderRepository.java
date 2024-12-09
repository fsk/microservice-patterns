package org.fsk.command.repository;

import org.fsk.command.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
