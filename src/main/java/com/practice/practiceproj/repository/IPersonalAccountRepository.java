package com.practice.practiceproj.repository;

import com.practice.practiceproj.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.Optional;
import java.util.UUID;

public interface IPersonalAccountRepository extends Repository<UserEntity, UUID> {
    Optional<UserEntity> findByMail(String mail);

    boolean existsByMail(String mail);

    void save(UserEntity entity);

}
