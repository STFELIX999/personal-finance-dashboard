package com.stevin.personalfinancedashboard.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stevin.personalfinancedashboard.entity.Category;
import com.stevin.personalfinancedashboard.repository.CategoryRepository;
import com.stevin.personalfinancedashboard.service.CategoryService;

@Service
public class CategoryServiceImpl
        implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(
            CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }
}