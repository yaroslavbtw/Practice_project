package com.practice.practiceproj.service;


import com.practice.practiceproj.core.dtos.ProductDTO;
import com.practice.practiceproj.entity.ProductEntity;
import com.practice.practiceproj.exceptions.SingleErrorResponse;
import com.practice.practiceproj.service.IService.IProductService;
import org.springframework.stereotype.Service;
import com.practice.practiceproj.repository.ProductRepository;
import java.time.Instant;
import java.util.UUID;
import com.practice.practiceproj.converters.ProductConverter;

@Service
public class ProductService implements IProductService {
    private final ProductRepository repository;
    private final ProductConverter converter;

    public ProductService(ProductRepository repository, ProductConverter converter) {
        this.repository = repository;
        this.converter = converter;
    }

    @Override
    public UUID add(ProductDTO product) {
        ProductEntity productEntity = converter.convertToEntity(product);
        productEntity.setUuid(UUID.randomUUID());
        productEntity.setDtCreate(Instant.now());
        productEntity.setDtUpdate(Instant.now());
        repository.save(productEntity);
        return productEntity.getUuid();
    }

    @Override
    public UUID update(UUID uuid, ProductDTO product) {
        ProductEntity productEntity = repository.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("error", "there is no product with this id : " + uuid));
//        if (productEntity.getDtUpdate().toEpochMilli() != dtUpdate.toEpochMilli()) {
//            throw new SingleErrorResponse("error", "product already has been update");
//        }
        productEntity.setTitle(product.getTitle());
        productEntity.setCalories(product.getCalories());
        productEntity.setProteins(product.getProteins());
        productEntity.setFats(product.getFats());
        productEntity.setCarbohydrates(product.getCarbohydrates());
        repository.save(productEntity);
        return uuid;
    }


    @Override
    public ProductEntity find(UUID uuid) {
        return repository.findById(uuid).orElseThrow(() ->
                new SingleErrorResponse("error", "there is no product with this id : " + uuid));
    }
}
