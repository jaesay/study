package toyproject.ecommerce.core.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.ecommerce.core.domain.CartItem;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @EntityGraph(attributePaths = "item")
    Optional<CartItem> findByItem_IdAndCart_Member_Email(Long itemId, String email);
    void deleteByItem_IdAndCart_Member_Email(Long itemId, String email);
    List<CartItem> findByModifiedDateBefore(LocalDateTime localDateTime);
}
