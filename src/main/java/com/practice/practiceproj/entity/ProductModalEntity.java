package com.practice.practiceproj.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

@Entity
@Table(name = "product_modal",schema = "public")
public class ProductModalEntity {
    @Id
    @Column(name = "uuid")
    @GeneratedValue
    private UUID uuid;
    @ManyToOne
    @NotNull
    private ProductEntity product;

    @Positive
    private Integer weight;

    public ProductModalEntity() {
    }

    public ProductModalEntity(ProductEntity product, Integer weight) {
        this.product = product;
        this.weight = weight;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

}