package com.practice.practiceproj.controller;

import com.practice.practiceproj.core.dtos.ReviewDTO;
import com.practice.practiceproj.entity.ReviewEntity;
import com.practice.practiceproj.repository.ReviewRepository;
import com.practice.practiceproj.service.IService.IReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewController {
    private final IReviewService service;
    private final ReviewRepository repository;

    public ReviewController(IReviewService service, ReviewRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping()
    Iterable<ReviewEntity> all(){
        return repository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody ReviewDTO review) {
        service.create(review);
        return ResponseEntity.status(201).build();
    }

    @PutMapping(value = "/{uuid}")
    public ResponseEntity<?> update(@PathVariable("uuid") UUID uuid,
                                    @Valid @RequestBody ReviewDTO review) {
        service.update(uuid, review);
        return ResponseEntity.status(200).build();
    }
}
