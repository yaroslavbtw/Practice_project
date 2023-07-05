package com.practice.practiceproj.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

@Entity
@Table(name = "favorite_recipes", schema = "public")
public class FavoriteRecipesEntity {

    @Id
    @GeneratedValue
    @Column(name = "uuid")
    private UUID uuid;

    @ManyToOne
    @NotNull
    private UserEntity user;

    @ManyToOne
    @NotNull
    private RecipeEntity recipe;

    public FavoriteRecipesEntity(@NotNull UserEntity user, @NotNull RecipeEntity recipe) {
        this.user = user;
        this.recipe = recipe;
    }

    public FavoriteRecipesEntity() {

    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
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
