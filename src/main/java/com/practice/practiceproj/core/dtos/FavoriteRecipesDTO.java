package com.practice.practiceproj.core.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.practice.practiceproj.entity.RecipeEntity;
import com.practice.practiceproj.entity.UserEntity;
import jakarta.validation.constraints.NotNull;

@JsonPropertyOrder({"uuid",
        "user_uuid",
        "recipe_uuid"})

public class FavoriteRecipesDTO {
    @NotNull
    private UserEntity user;

    @NotNull
    private RecipeEntity recipe;

    public FavoriteRecipesDTO(@NotNull UserEntity user, @NotNull RecipeEntity recipe) {
        this.user = user;
        this.recipe = recipe;
    }

    public FavoriteRecipesDTO(){

    }

    public RecipeEntity getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
