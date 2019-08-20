package com.demoecommerce.web.service;

import com.demoecommerce.domain.entity.Category;
import com.demoecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public boolean existsById(String categoryId) {
        return categoryRepository.existsById(categoryId);
    }
}
