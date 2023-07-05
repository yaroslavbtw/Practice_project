package com.practice.practiceproj.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "reviews", schema = "public")
public class ReviewEntity {
    @Id
    @GeneratedValue
    @Column(name = "uuid")
    private UUID uuid;
    private String text;
    @ManyToOne
    @JoinColumn(name = "recipe_uuid")
    private RecipeEntity recipe;
    @ManyToOne
    @JoinColumn(name = "parent_review_uuid")
    private ReviewEntity parentReview;
    @ManyToOne
    @JoinColumn(name = "user_uuid")
    private UserEntity user;
    private Integer likes;
    @Column(name = "dt_create")
    private Instant dtCreate;
    @Positive
    @Min(0)
    @Max(5)
    private Integer rating;

    public ReviewEntity(UUID uuid, String text, RecipeEntity recipe, ReviewEntity parentReview, UserEntity user,
                        Integer likes, Instant dtCreate, Integer rating) {
        this.uuid = uuid;
        this.text = text;
        this.recipe = recipe;
        this.parentReview = parentReview;
        this.user = user;
        this.likes = likes;
        this.dtCreate = dtCreate;
        this.rating = rating;
    }

    public ReviewEntity() {

    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RecipeEntity getRecipe() {
        return recipe;
    }

    public void setRecipe(RecipeEntity recipe) {
        this.recipe = recipe;
    }

    public ReviewEntity getParentReview() {
        return parentReview;
    }

    public void setParentReview(ReviewEntity parentReview) {
        this.parentReview = parentReview;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Instant getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(Instant dtCreate) {
        this.dtCreate = dtCreate;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
