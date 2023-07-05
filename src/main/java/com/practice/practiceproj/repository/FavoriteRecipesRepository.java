package com.practice.practiceproj.repository;

import com.practice.practiceproj.entity.FavoriteRecipesEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FavoriteRecipesRepository extends CrudRepository<FavoriteRecipesEntity, UUID> {
}
