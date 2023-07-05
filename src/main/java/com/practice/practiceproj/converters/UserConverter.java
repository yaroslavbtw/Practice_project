package com.practice.practiceproj.converters;

import com.practice.practiceproj.core.dtos.user.*;
import com.practice.practiceproj.entity.UserEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class UserConverter {

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserDTO convertToUserDTO(UserEntity entity) {
        return new UserDTO(entity.getUuid(), entity.getDtCreate(),
                entity.getDtUpdate(), entity.getMail(),
                entity.getFio(), entity.getRole(), entity.getStatus());
    }

    public UserEntity convertToUserEntity(UserCreateDTO user) {
        UserEntity entity = new UserEntity();
        entity.setMail(user.getMail());
        entity.setFio(user.getFio());
        entity.setRole(user.getRole());
        entity.setStatus(user.getStatus());
        entity.setPassword(encoder.encode(user.getPassword()));
        return entity;
    }

    public UserEntity converToUserEntity(UserRegistrationDTO userDTO) {
        UserEntity entity = new UserEntity();
        entity.setMail(userDTO.getMail());
        entity.setFio(userDTO.getFio());
        entity.setPassword(encoder.encode(userDTO.getPassword()));
        entity.setRole(UserRoleDTO.USER);
        entity.setStatus(UserStatusDTO.WAITING_ACTIVATION);
        return entity;
    }

    public MyUserDetails convertToUserDetails(UserEntity entity){
        return new MyUserDetails(entity.getUuid(),entity.getDtCreate(),entity.getDtUpdate(),
                entity.getMail(),entity.getFio(),entity.getRole(),
                entity.getStatus(), entity.getPassword());
    }
}
