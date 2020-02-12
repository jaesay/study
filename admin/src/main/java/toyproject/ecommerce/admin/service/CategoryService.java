package toyproject.ecommerce.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.ecommerce.core.domain.Category;
import toyproject.ecommerce.core.repository.CategoryRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Category findById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(IllegalStateException::new);
    }

    public List<Category> findChildCategories() {
        return categoryRepository.findByParent_IdIsNotNull();
    }
}
