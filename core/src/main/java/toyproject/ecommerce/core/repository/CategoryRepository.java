package toyproject.ecommerce.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.ecommerce.core.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
