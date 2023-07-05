package com.practice.practiceproj.repository;

import com.practice.practiceproj.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import java.util.UUID;

public interface UserRepository extends CrudRepository<UserEntity, UUID> {

    boolean existsByMail(String mail);

    UserEntity findByMail(String mail);
}
