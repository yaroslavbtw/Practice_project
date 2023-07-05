package com.practice.practiceproj.core.dtos;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.*;

import java.time.Instant;
import java.util.UUID;

@JsonPropertyOrder(value = {
        "uuid",
        "text",
        "recipe_id",
        "parent_review_id",
        "user_id",
        "likes",
        "dt_create",
        "rating"
})

public class ReviewDTO {
    @NotBlank
    private String text;
    @NotNull
    private UUID recipeId;

    private UUID parentReviewId;
    @NotNull
    private UUID userId;
    @Positive
    @Min(0)
    private Integer likes;
    @Min(0)
    @Max(5)
    @NotNull
    private Integer rating;

    public ReviewDTO(String text, @NotNull UUID recipeId, UUID parentReviewId, @NotNull UUID userId, Integer likes, Integer rating) {
        this.text = text;
        this.recipeId = recipeId;
        this.parentReviewId = parentReviewId;
        this.userId = userId;
        this.likes = likes;
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public @NotNull UUID getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(@NotNull UUID recipeId) {
        this.recipeId = recipeId;
    }

    public UUID getParentReviewId() {
        return parentReviewId;
    }

    public void setParentReviewId(UUID parentReviewId) {
        this.parentReviewId = parentReviewId;
    }

    public @NotNull UUID getUserId() {
        return userId;
    }

    public void setUserId(@NotNull UUID userId) {
        this.userId = userId;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
