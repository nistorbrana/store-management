package com.learning.storemanagement.service;

import com.learning.storemanagement.dto.ProductDTO;
import com.learning.storemanagement.dto.builder.ProductBuilder;
import com.learning.storemanagement.model.Product;
import com.learning.storemanagement.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private ProductRepository productRepository;

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(ProductBuilder::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = ProductBuilder.toEntity(productDTO);
        return ProductBuilder.toDTO(productRepository.save(product));
    }
}
