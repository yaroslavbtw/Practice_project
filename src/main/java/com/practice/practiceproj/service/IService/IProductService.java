package com.practice.practiceproj.service.IService;

import com.practice.practiceproj.core.dtos.ProductDTO;
import com.practice.practiceproj.entity.ProductEntity;
import java.util.UUID;

public interface IProductService {
    UUID add(ProductDTO product);

    UUID update(UUID uuid, ProductDTO product);

    ProductEntity find(UUID uuid);

}
