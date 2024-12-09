package org.fsk.command.repository;

import java.util.Optional;

import org.fsk.command.entity.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByOrderId(Long orderId);

    Optional<Shipment> findByTrackingNumber(String trackingNumber);

}
