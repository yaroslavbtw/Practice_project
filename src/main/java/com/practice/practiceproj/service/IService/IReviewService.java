package com.practice.practiceproj.service.IService;

import com.practice.practiceproj.core.dtos.ReviewDTO;

import java.util.UUID;

public interface IReviewService {

    UUID create(ReviewDTO review);

    ReviewDTO get(UUID uuid);

    UUID update(UUID uuid, ReviewDTO review);

}
