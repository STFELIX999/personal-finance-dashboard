package com.stevin.personalfinancedashboard.service;

import java.util.List;

import com.stevin.personalfinancedashboard.dto.CategoryRequest;
import com.stevin.personalfinancedashboard.dto.CategoryResponse;
import com.stevin.personalfinancedashboard.entity.Category;

public interface CategoryService {

    Category saveCategory(CategoryRequest request);

    List<Category> getAllCategories();
    List<CategoryResponse> getAllCategoryResponses();
}