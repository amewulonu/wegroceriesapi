package com.wegroceries.wegroceriesapi.categories;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category createCategory(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            throw new IllegalArgumentException("Category name already exists.");
        }
        return categoryRepository.save(category);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Optional<Category> getCategoryById(UUID id) {
        return categoryRepository.findById(id);
    }

    public void deleteCategory(UUID id) {
        if (!categoryRepository.existsById(id)) {
            throw new IllegalArgumentException("Category not found.");
        }
        categoryRepository.deleteById(id);
    }

    public Object getCategoriesByName(String string) {

        throw new UnsupportedOperationException("Unimplemented method 'getCategoriesByName'");
    }

    public Object updateCategory(Object any, Object any2) {
        throw new UnsupportedOperationException("Unimplemented method 'updateCategory'");
    }
}

