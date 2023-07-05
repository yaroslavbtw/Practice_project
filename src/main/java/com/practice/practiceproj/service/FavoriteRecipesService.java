package com.practice.practiceproj.service;

import com.practice.practiceproj.core.dtos.FavoriteRecipesCreateDTO;
import com.practice.practiceproj.entity.FavoriteRecipesEntity;
import com.practice.practiceproj.entity.ProductEntity;
import com.practice.practiceproj.entity.UserEntity;
import com.practice.practiceproj.exceptions.SingleErrorResponse;
import com.practice.practiceproj.repository.FavoriteRecipesRepository;
import com.practice.practiceproj.repository.UserRepository;
import com.practice.practiceproj.service.IService.IFavoriteRecipesService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class FavoriteRecipesService implements IFavoriteRecipesService {
    private final FavoriteRecipesRepository repository;

    private final UserRepository userRepository;

    public FavoriteRecipesService(FavoriteRecipesRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    @Override
    public UUID add(FavoriteRecipesCreateDTO favoriteRecipesCreateDTO) {
        FavoriteRecipesEntity favoriteRecipesEntity = new FavoriteRecipesEntity();
        favoriteRecipesEntity.setUuid(UUID.randomUUID());
        Optional<FavoriteRecipesEntity> recipe = repository.findById(favoriteRecipesCreateDTO.getRecipe_uuid());
        if(recipe.isEmpty())
            throw new SingleErrorResponse("error", "there is no favorite_recipes with this recipe_uuid");
        favoriteRecipesEntity.setRecipe(recipe.get().getRecipe());
        Optional<UserEntity> user = userRepository.findById(favoriteRecipesCreateDTO.getUser_uuid());
        if(user.isEmpty())
            throw new SingleErrorResponse("error", "there is no favorite_recipes with this user_uuid");
        favoriteRecipesEntity.setUser(user.get());
        repository.save(favoriteRecipesEntity);
        return favoriteRecipesEntity.getUuid();
    }

    @Override
    public UUID update(UUID uuid, FavoriteRecipesCreateDTO favoriteRecipesCreateDTO) {
        FavoriteRecipesEntity favoriteRecipesEntity = repository.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("error", "there is no favorite_recipes with this id : " + uuid));

        Optional<FavoriteRecipesEntity> recipe = repository.findById(favoriteRecipesCreateDTO.getRecipe_uuid());
        if(recipe.isEmpty())
            throw new SingleErrorResponse("error", "there is no favorite_recipes with this recipe_uuid");
        favoriteRecipesEntity.setRecipe(recipe.get().getRecipe());
        Optional<UserEntity> user = userRepository.findById(favoriteRecipesCreateDTO.getUser_uuid());
        if(user.isEmpty())
            throw new SingleErrorResponse("error", "there is no favorite_recipes with this user_uuid");
        favoriteRecipesEntity.setUser(user.get());
        repository.save(favoriteRecipesEntity);
        return favoriteRecipesEntity.getUuid();
    }

    @Override
    public FavoriteRecipesEntity find(UUID uuid) {
        return repository.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("error", "there is no favorite_recipes with this id : " + uuid));
    }

    public FavoriteRecipesRepository getRepository() {
        return repository;
    }
}
