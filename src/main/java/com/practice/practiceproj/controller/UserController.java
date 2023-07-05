package com.practice.practiceproj.controller;

import com.practice.practiceproj.core.dtos.user.*;
import com.practice.practiceproj.entity.UserEntity;
import com.practice.practiceproj.repository.UserRepository;
import com.practice.practiceproj.service.IService.IUserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController()
@RequestMapping("/api/v1/users")
public class UserController {
    private final IUserService service;
    private final UserRepository repository;

    public UserController(IUserService service, UserRepository repository) {
        this.service = service;
        this.repository = repository;
    }

    @GetMapping()
    Iterable<UserEntity> all(){
        return repository.findAll();
    }


    @GetMapping(path = "/{uuid}")
    public ResponseEntity<UserDTO> get(@PathVariable("uuid") UUID uuid) {
        return ResponseEntity.status(200).body(service.get(uuid));
    }

    @PostMapping
    public UserDTO create(@Valid @RequestBody UserCreateDTO user) {
        return service.create(user);
    }

    @PutMapping(path = "/{uuid}")
    public UserDTO update(@PathVariable("uuid") UUID uuid,
                                    @Valid @RequestBody UserCreateDTO user) {
        return service.update(uuid, user);
    }

    @DeleteMapping(path = "/{uuid}")
    public Optional<UserEntity> delete(@PathVariable("uuid") UUID uuid) {
        Optional<UserEntity> user = repository.findById(uuid);
        repository.deleteById(uuid);
        return user;
    }

}
