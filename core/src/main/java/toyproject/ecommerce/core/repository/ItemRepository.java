package toyproject.ecommerce.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.ecommerce.core.domain.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
