package com.stevin.personalfinancedashboard.controller;

import java.util.List;

import com.stevin.personalfinancedashboard.dto.CategoryRequest;
import com.stevin.personalfinancedashboard.dto.CategoryResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import com.stevin.personalfinancedashboard.entity.Category;
import com.stevin.personalfinancedashboard.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(
            CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public Category createCategory(
            @Valid
            @RequestBody CategoryRequest request) {

        return categoryService.saveCategory(request);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @GetMapping("/safe")
    public List<CategoryResponse> getSafeUsers() {
        return categoryService.getAllCategoryResponses();
    }
}