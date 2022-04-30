package toyproject.ecommerce.web.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import toyproject.ecommerce.core.domain.entity.Category;
import toyproject.ecommerce.core.repository.CategoryRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getShopCategories() {

        return categoryRepository.findByParent_IdIsNotNull();
    }
}
