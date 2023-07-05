package com.practice.practiceproj.service;

import com.practice.practiceproj.converters.UserConverter;
import com.practice.practiceproj.core.dtos.user.*;
import com.practice.practiceproj.entity.UserEntity;
import com.practice.practiceproj.exceptions.SingleErrorResponse;
import com.practice.practiceproj.repository.UserRepository;
import com.practice.practiceproj.service.IService.IUserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final UserRepository repository;
    private final UserConverter converter;

    public UserService(UserRepository repository, UserConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }


    @Override
    public UserDTO create(UserCreateDTO user) {
        if (repository.existsByMail(user.getMail())) {
            throw new SingleErrorResponse("error", "user with this email already exist");
        }
        UserEntity entity = converter.convertToUserEntity(user);
        UUID uuid = UUID.randomUUID();
        entity.setUuid(uuid);
        entity.setDtCreate(Instant.now());
        entity.setDtUpdate(Instant.now());
        return converter.convertToUserDTO(repository.save(entity));
    }

    @Override
    public UserDTO get(UUID uuid) {
        UserEntity user = repository.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("error", "invalid user uuid"));
        return converter.convertToUserDTO(user);
    }


    @Override
    public UserDTO update(UUID uuid, UserCreateDTO createDTO) {
        UserEntity user = repository.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("error", "invalid user uuid"));
//        if (dtUpdate.toEpochMilli() != user.getDtUpdate().toEpochMilli()) {
//            throw new SingleErrorResponse("error", "user has already been updated");
//        }
        user.setMail(createDTO.getMail());
        user.setFio(createDTO.getFio());
        user.setRole(createDTO.getRole());
        user.setStatus(createDTO.getStatus());
        user.setPassword(createDTO.getPassword());
        return converter.convertToUserDTO(repository.save(user));
    }

}