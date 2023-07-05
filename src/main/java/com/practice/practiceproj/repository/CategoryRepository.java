package com.practice.practiceproj.repository;

import com.practice.practiceproj.core.dtos.CategoryDTO;
import com.practice.practiceproj.entity.CategoryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, UUID> {
    CategoryEntity findByTitle(String title);
}
