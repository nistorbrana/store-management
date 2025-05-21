package com.learning.storemanagement.service;

import com.learning.storemanagement.dto.ProductDTO;
import com.learning.storemanagement.dto.ChangePriceRequest;
import com.learning.storemanagement.dto.builder.ProductBuilder;
import com.learning.storemanagement.exception.ResourceNotFoundException;
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
    private ProductBuilder productBuilder;

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream()
                .map(productBuilder::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = productBuilder.toEntity(productDTO);
        return productBuilder.toDTO(productRepository.save(product));
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return productBuilder.toDTO(product);

    }

    public ProductDTO updatePrice(Long productId, ChangePriceRequest newPriceDTO) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + productId + " not found"));

        product.setPrice(newPriceDTO.getNewPrice());

        return productBuilder.toDTO(productRepository.save(product));
    }
}
