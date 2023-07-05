package com.practice.practiceproj.controller;

import com.practice.practiceproj.converters.RecipeConverter;
import com.practice.practiceproj.core.dtos.RecipeDTO;
import com.practice.practiceproj.core.dtos.RecipeForCUDTO;
import com.practice.practiceproj.entity.RecipeEntity;
import com.practice.practiceproj.repository.RecipeRepository;
import com.practice.practiceproj.service.IService.IRecipeService;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/api/v1/recipes")
public class RecipeController {

    private final IRecipeService service;
    private final RecipeRepository repository;

    private final RecipeConverter converter;

    public RecipeController(IRecipeService service, RecipeRepository repository, RecipeConverter converter) {
        this.service = service;
        this.repository = repository;
        this.converter = converter;
    }

    @GetMapping()
    Stream<RecipeDTO> all(){
        Stream<RecipeEntity> stream = StreamSupport.stream(repository.findAll().spliterator(), false);
        return stream.map(converter::convertTORecipeDTO);
    }


    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody RecipeForCUDTO recipe) {
        service.add(recipe);
        return ResponseEntity.status(201).build();
    }

    @PutMapping(value = "/{uuid}")
    public ResponseEntity<?> update(@PathVariable("uuid") UUID uuid,
                                    @Valid @RequestBody RecipeForCUDTO recipe) {
        service.update(uuid, recipe);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping(path = "/{uuid}")
    public void delete(@PathVariable("uuid") UUID uuid) {
        repository.deleteById(uuid);
    }
}