package de.consol.customer.boundary.repository;

import de.consol.customer.entity.OrderEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
  List<OrderEntity> findByCustomerEmail(String customerEmail);
}
