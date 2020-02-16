package toyproject.ecommerce.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import toyproject.ecommerce.core.domain.entity.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByParent_IdIsNotNull();
}
