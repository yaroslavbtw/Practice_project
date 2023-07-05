package com.practice.practiceproj.service;

import com.practice.practiceproj.converters.UserConverter;
import com.practice.practiceproj.core.dtos.user.*;
import com.practice.practiceproj.entity.UserEntity;
import com.practice.practiceproj.exceptions.SingleErrorResponse;
import com.practice.practiceproj.repository.IPersonalAccountRepository;
import com.practice.practiceproj.security.JwtTokenUtil;
import com.practice.practiceproj.security.MyUserDetailsService;
import com.practice.practiceproj.security.UserHolder;
import com.practice.practiceproj.service.IService.IPersonalAccountService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonalAccountService implements IPersonalAccountService {
    private final IPersonalAccountRepository repository;
    private final UserConverter converter;
    private final BCryptPasswordEncoder encoder;
    private final UserHolder userHolder;
    private final JwtTokenUtil tokenUtil;
    private final MyUserDetailsService userDetailsService;

    public PersonalAccountService(IPersonalAccountRepository repository,
                                  UserConverter converter,
                                  BCryptPasswordEncoder encoder,
                                  UserHolder userHolder,
                                  JwtTokenUtil tokenUtil,
                                  MyUserDetailsService userDetailsService) {
        this.repository = repository;
        this.converter = converter;
        this.encoder = encoder;
        this.userHolder = userHolder;
        this.tokenUtil = tokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public String login(UserLoginDTO user) {
        UserEntity entity = repository.findByMail(user.getMail()).orElseThrow(() ->
                new SingleErrorResponse("error", "user with email: " + user.getMail()
                        + " not found"));
        if (entity.getStatus() == UserStatusDTO.WAITING_ACTIVATION) {
            throw new SingleErrorResponse("error", "authorization is not available," +
                    " the account is not verified");
        }
        if (encoder.matches(user.getPassword(), entity.getPassword())) {
            MyUserDetails userDetails = userDetailsService.loadUserByUsername(user.getMail());
            return tokenUtil.generateAccessToken(userDetails);
        } else {
            throw new SingleErrorResponse("error", "invalid password");
        }
    }

    @Override
    public UserDTO register(UserRegistrationDTO user) {
        if (repository.existsByMail(user.getMail())) {
            throw new SingleErrorResponse("error", "user with this mail already exist");
        }
        UserEntity entity = converter.converToUserEntity(user);
        entity.setUuid(UUID.randomUUID());
        entity.setDtCreate(Instant.now());
        entity.setDtUpdate(Instant.now());
        repository.save(entity);
        return converter.convertToUserDTO(entity);
    }

    @Override
    public void verified(String code, String mail) {
        UserEntity user = repository.findByMail(mail).orElseThrow(() ->
                new SingleErrorResponse("error", "user with this mail: " + mail
                        + " not found"));

//        if (mailService.checkVerification(mail, code)) {
//            user.setStatus(new UserStatusEntity(UserStatus.ACTIVATED));
//            repository.save(user);
//        } else {
//            throw new SingleErrorResponse("error", "invalid verification code");
//        }
//        return user.getUuid();
    }

    @Override
    public UserDTO getMe() {
        return converter.convertToUserDTO(
                repository.findByMail(
                                userHolder.getUser().getUsername())
                        .orElseThrow(() ->
                                new SingleErrorResponse("error",
                                        "user with this email not found")));
    }
}
