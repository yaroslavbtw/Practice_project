package com.practice.practiceproj.service.IService;

import com.practice.practiceproj.core.dtos.user.UserDTO;
import com.practice.practiceproj.core.dtos.user.UserLoginDTO;
import com.practice.practiceproj.core.dtos.user.UserRegistrationDTO;

public interface IPersonalAccountService {

    String login(UserLoginDTO user);

    UserDTO register(UserRegistrationDTO user);

    void verified(String code, String mail);

    Object getMe();
}
