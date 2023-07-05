package com.practice.practiceproj.controller;

import com.practice.practiceproj.core.dtos.CategoryDTO;
import com.practice.practiceproj.entity.CategoryEntity;
import com.practice.practiceproj.repository.CategoryRepository;
import com.practice.practiceproj.service.IService.ICategoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {
    private final ICategoryService service;
    private final CategoryRepository repository;

    public CategoryController(ICategoryService service, CategoryRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping()
    Iterable<CategoryEntity> all(){
        return repository.findAll();
    }


    @GetMapping(path = "/{uuid}")
    public ResponseEntity<CategoryDTO> get(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.status(200).body(service.find(uuid));
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody CategoryDTO category) {
        service.add(category);
        return ResponseEntity.status(201).build();
    }

    @PutMapping(path = "/{uuid}")
    public ResponseEntity<?> update(@PathVariable("uuid") UUID uuid,
                                    @Valid @RequestBody CategoryDTO category) {
        service.update(uuid, category);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping(path = "/{uuid}")
    public void delete(@PathVariable("uuid") UUID uuid) {
        repository.deleteById(uuid);
    }
}
