package toyproject.ecommerce.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.ecommerce.core.domain.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMember_Id(Long memberId);
}
