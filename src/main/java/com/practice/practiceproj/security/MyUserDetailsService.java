package com.practice.practiceproj.security;

import com.practice.practiceproj.core.dtos.user.MyUserDetails;
import com.practice.practiceproj.repository.IPersonalAccountRepository;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.practice.practiceproj.converters.UserConverter;

@Component
public class MyUserDetailsService implements UserDetailsService {
    private final IPersonalAccountRepository repository;
    private final UserConverter converter;

    public MyUserDetailsService(IPersonalAccountRepository repository,
                                UserConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public MyUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return converter.convertToUserDetails(repository.findByMail(username).orElseThrow(() ->
                new UsernameNotFoundException("user with this mail not found")));
    }
}
