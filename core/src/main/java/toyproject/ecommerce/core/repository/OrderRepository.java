package toyproject.ecommerce.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.ecommerce.core.domain.Order;
import toyproject.ecommerce.core.domain.enums.OrderStatus;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    Optional<Order> findByIdAndStatus(Long id, OrderStatus status);
}