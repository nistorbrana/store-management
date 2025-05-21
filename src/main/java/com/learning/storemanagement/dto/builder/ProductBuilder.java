package com.learning.storemanagement.dto.builder;

import com.learning.storemanagement.dto.ProductDTO;
import com.learning.storemanagement.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductBuilder {

    public Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .build();
    }

    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .stock(product.getStock())
                .build();
    }
}
