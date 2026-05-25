package com.stevin.personalfinancedashboard.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.stevin.personalfinancedashboard.entity.Category;

public interface CategoryRepository
        extends JpaRepository<Category, Long> {

}