package toyproject.ecommerce.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.ecommerce.core.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}