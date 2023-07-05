package com.practice.practiceproj.converters;

import com.practice.practiceproj.core.dtos.ProductCompositionDTO;
import com.practice.practiceproj.core.dtos.ProductDTO;
import com.practice.practiceproj.entity.ProductEntity;
import com.practice.practiceproj.entity.ProductModalEntity;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    public ProductConverter() {
    }

    public ProductEntity convertToEntity(ProductDTO product) {
        return new ProductEntity(product.getTitle(),
                product.getCalories(), product.getProteins(), product.getFats(),
                product.getCarbohydrates());
    }

    public ProductDTO convertToProductDTO(ProductEntity product) {
        return new ProductDTO(product.getUuid(), product.getDtCreate(),
                product.getDtUpdate(), product.getTitle(), product.getCalories(), product.getProteins(),
                product.getFats(), product.getCarbohydrates());
    }


    public ProductCompositionDTO convertToProductCompositionDTO(ProductModalEntity productModel) {
        ProductCompositionDTO compositionDTO = new ProductCompositionDTO(
                convertToProductDTO(productModel.getProduct()));
        compositionDTO.setWeight(productModel.getWeight());
        var prod = productModel.getProduct();
        double koef = (double) productModel.getWeight() / 100;
        compositionDTO.setCalories((int) Math.round(koef * prod.getCalories()));
        compositionDTO.setProteins(koef * prod.getProteins());
        compositionDTO.setFats(koef * prod.getFats());
        compositionDTO.setCarbohydrates(koef * prod.getCarbohydrates());
        return compositionDTO;
    }
}
