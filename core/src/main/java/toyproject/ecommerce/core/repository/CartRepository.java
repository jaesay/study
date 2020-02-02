package toyproject.ecommerce.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.ecommerce.core.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
