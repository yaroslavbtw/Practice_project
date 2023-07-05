package com.practice.practiceproj.service;

import com.practice.practiceproj.core.dtos.ReviewDTO;
import com.practice.practiceproj.entity.RecipeEntity;
import com.practice.practiceproj.entity.ReviewEntity;
import com.practice.practiceproj.entity.UserEntity;
import com.practice.practiceproj.exceptions.SingleErrorResponse;
import com.practice.practiceproj.repository.RecipeRepository;
import com.practice.practiceproj.repository.ReviewRepository;
import com.practice.practiceproj.repository.UserRepository;
import com.practice.practiceproj.service.IService.IReviewService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class ReviewService implements IReviewService {
    private final ReviewRepository repository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository repository, RecipeRepository recipeRepository, UserRepository userRepository) {
        this.repository = repository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public UUID create(ReviewDTO review) {
        ReviewEntity entity = new ReviewEntity();
        entity.setUuid(UUID.randomUUID());
        entity.setDtCreate(Instant.now());
        entity.setText(review.getText());
        Optional<ReviewEntity> parent;
        if(review.getParentReviewId() != null) {
            parent = repository.findById(review.getParentReviewId());
            if (parent.isEmpty())
                throw new SingleErrorResponse("error", "parent review with this uuid does not exist");
            entity.setParentReview(parent.get());
        }
        else entity.setParentReview(null);

        Optional<RecipeEntity> recipe = recipeRepository.findById(review.getRecipeId());
        if(recipe.isEmpty())
            throw new SingleErrorResponse("error", "recipe with this uuid does not exist");
        entity.setRecipe(recipe.get());

        Optional<UserEntity> user = userRepository.findById(review.getUserId());
        if(user.isEmpty())
            throw new SingleErrorResponse("error", "user with this uuid does not exist");
        entity.setUser(user.get());

        entity.setRating(review.getRating());
        entity.setLikes(0);
        repository.save(entity);
        return entity.getUuid();
    }

    @Override
    public ReviewDTO get(UUID uuid) {
        return null;
    }

    @Override
    public UUID update(UUID uuid, ReviewDTO review) {
        return null;
    }
}
