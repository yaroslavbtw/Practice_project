package com.practice.practiceproj.service.IService;

import com.practice.practiceproj.core.dtos.RecipeForCUDTO;

import java.util.UUID;

public interface IRecipeService {
    UUID add(RecipeForCUDTO recipe);

    UUID update(UUID uuid, RecipeForCUDTO recipe);

}
