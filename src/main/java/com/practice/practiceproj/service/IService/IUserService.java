package com.practice.practiceproj.service.IService;

import com.practice.practiceproj.core.dtos.user.UserCreateDTO;
import com.practice.practiceproj.core.dtos.user.UserDTO;
import com.practice.practiceproj.core.dtos.user.UserLoginDTO;
import com.practice.practiceproj.core.dtos.user.UserRegistrationDTO;

import java.util.UUID;

public interface IUserService {
    UserDTO create(UserCreateDTO user);

    UserDTO get(UUID uuid);

    UserDTO update(UUID uuid, UserCreateDTO user);

}
