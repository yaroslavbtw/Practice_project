package com.practice.practiceproj.repository;

import com.practice.practiceproj.entity.ReviewEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface ReviewRepository extends CrudRepository<ReviewEntity, UUID> {

}
