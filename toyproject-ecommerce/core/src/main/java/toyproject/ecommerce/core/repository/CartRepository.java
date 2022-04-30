package toyproject.ecommerce.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.ecommerce.core.domain.entity.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByMember_Id(Long memberId);
    @EntityGraph(attributePaths = {"cartItems", "cartItems.item"})
    Optional<Cart> findByMember_Email(String email);
}
