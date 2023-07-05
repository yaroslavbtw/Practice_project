package com.practice.practiceproj.service.IService;

import com.practice.practiceproj.entity.FavoriteRecipesEntity;
import com.practice.practiceproj.core.dtos.FavoriteRecipesCreateDTO;

import java.util.UUID;

public interface IFavoriteRecipesService {

    UUID add(FavoriteRecipesCreateDTO favoriteRecipesDTO);

    UUID update(UUID uuid, FavoriteRecipesCreateDTO favoriteRecipesDTO);

    FavoriteRecipesEntity find(UUID uuid);
}
