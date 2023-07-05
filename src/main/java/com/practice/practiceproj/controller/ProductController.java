package com.practice.practiceproj.controller;

import com.practice.practiceproj.converters.ProductConverter;
import com.practice.practiceproj.entity.ProductEntity;
import com.practice.practiceproj.repository.ProductRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.practice.practiceproj.service.ProductService;
import com.practice.practiceproj.core.dtos.ProductDTO;

import java.util.UUID;

@RestController()
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductRepository repository;
    private final ProductService service;
    private final ProductConverter converter;

    public ProductController(ProductRepository repository, ProductService service, ProductConverter converter) {
        this.repository = repository;
        this.service = service;
        this.converter = converter;
    }

    @GetMapping("/")
    Iterable<ProductEntity> all(){
        return repository.findAll();
    }

    @GetMapping(path = "/{uuid}")
    public ResponseEntity<ProductDTO> get(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.status(200).body(converter.convertToProductDTO(service.find(uuid)));
    }

    @PostMapping
    public ResponseEntity<?> add(@Valid @RequestBody ProductDTO product) {
        service.add(product);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<?>update(@PathVariable(name = "uuid") UUID uuid,
                                   @Valid @RequestBody ProductDTO productDTO) {
        service.update(uuid, productDTO);
        return ResponseEntity.status(200).build();
    }

    @DeleteMapping(path = "/{uuid}")
    public void delete(@PathVariable("uuid") UUID uuid) {
        repository.deleteById(uuid);
    }
}