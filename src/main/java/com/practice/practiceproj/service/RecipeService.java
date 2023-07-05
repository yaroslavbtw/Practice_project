package com.practice.practiceproj.service;

import com.practice.practiceproj.core.dtos.RecipeForCUDTO;
import com.practice.practiceproj.repository.RecipeRepository;
import com.practice.practiceproj.service.IService.IRecipeService;
import org.springframework.stereotype.Service;
import com.practice.practiceproj.converters.RecipeConverter;
import com.practice.practiceproj.entity.RecipeEntity;
import com.practice.practiceproj.exceptions.SingleErrorResponse;
import java.time.Instant;
import java.util.UUID;

@Service
public class RecipeService implements IRecipeService {

    private final RecipeRepository repository;
    private final RecipeConverter converter;


    public RecipeService(RecipeRepository repository, RecipeConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public UUID add(RecipeForCUDTO recipe) {
        RecipeEntity entity = converter.convertToRecipeEntity(recipe);
        entity.setUuid(UUID.randomUUID());
        entity.setDtCreate(Instant.now());
        entity.setDtUpdate(Instant.now());
        repository.save(entity);
        System.out.println(entity.getCategory());
        return entity.getUuid();
    }


    @Override
    public UUID update(UUID uuid, RecipeForCUDTO recipe) {
        RecipeEntity entity = repository.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("error",
                        "recipe with this id: " + uuid + " not found"));
//        if (entity.getDtUpdate().toEpochMilli() != dtUpdate.toEpochMilli()) {
//            throw new SingleErrorResponse("err",
//                    "recipe already has been update");
//        }
        var newEntity = converter.convertToRecipeEntity(recipe);
        entity.setComposition(newEntity.getComposition());
        entity.setTitle(newEntity.getTitle());
        repository.save(entity);
        return uuid;
    }

}
