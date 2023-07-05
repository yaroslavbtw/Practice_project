package com.practice.practiceproj.core.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@JsonPropertyOrder({"uuid",
        "user_uuid",
        "recipe_uuid"})
public class FavoriteRecipesCreateDTO {

    @NotNull
    private UUID user_uuid;
    @NotNull
    private UUID recipe_uuid;

    public FavoriteRecipesCreateDTO(@NotNull UUID userUuid, @NotNull UUID recipeUuid) {
        user_uuid = userUuid;
        recipe_uuid = recipeUuid;
    }

    public UUID getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(UUID user_uuid) {
        this.user_uuid = user_uuid;
    }

    public UUID getRecipe_uuid() {
        return recipe_uuid;
    }

    public void setRecipe_uuid(UUID recipe_uuid) {
        this.recipe_uuid = recipe_uuid;
    }
}
