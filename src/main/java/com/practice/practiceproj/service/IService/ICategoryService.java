package com.practice.practiceproj.service.IService;

import com.practice.practiceproj.core.dtos.CategoryDTO;
import com.practice.practiceproj.entity.CategoryEntity;

import java.util.UUID;

public interface ICategoryService {
    UUID add(CategoryDTO category);

    UUID update(UUID uuid, CategoryDTO category);

    CategoryDTO find(UUID uuid);

    CategoryEntity findByTitle(String title);
}
