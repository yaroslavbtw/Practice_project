package com.practice.practiceproj.controller;

import com.practice.practiceproj.core.dtos.FavoriteRecipesCreateDTO;
import com.practice.practiceproj.entity.FavoriteRecipesEntity;
import com.practice.practiceproj.repository.FavoriteRecipesRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.practice.practiceproj.service.IService.IFavoriteRecipesService;

import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/favorite_recipes")
public class FavoriteRecipesController {
    private final IFavoriteRecipesService service;
    private final FavoriteRecipesRepository repository;

    public FavoriteRecipesController(IFavoriteRecipesService service, FavoriteRecipesRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping()
    Iterable<FavoriteRecipesEntity> all(){
        return repository.findAll();
    }


    @GetMapping(path = "/{uuid}")
    public ResponseEntity<FavoriteRecipesEntity> get(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.status(200).body(service.find(uuid));
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody FavoriteRecipesCreateDTO favoriteRecipesCreateDTO) {
        service.add(favoriteRecipesCreateDTO);
        return ResponseEntity.status(201).build();
    }

    @PutMapping(path = "/{uuid}")
    public ResponseEntity<?> update(@PathVariable("uuid") UUID uuid,
                                    @Valid @RequestBody FavoriteRecipesCreateDTO favoriteRecipesCreateDTO) {
        service.update(uuid, favoriteRecipesCreateDTO);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping(path = "/{uuid}")
    public void delete(@PathVariable("uuid") UUID uuid) {
        repository.deleteById(uuid);
    }

}
