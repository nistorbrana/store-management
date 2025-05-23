package com.learning.storemanagement.service;

import com.learning.storemanagement.dto.ProductDTO;
import com.learning.storemanagement.dto.ChangePriceRequest;
import com.learning.storemanagement.dto.builder.ProductBuilder;
import com.learning.storemanagement.exception.ResourceNotFoundException;
import com.learning.storemanagement.model.Product;
import com.learning.storemanagement.repository.ProductRepository;
import com.learning.storemanagement.utils.LoggerUtility;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.learning.storemanagement.utils.events.ProductEvent.*;

@Service
@AllArgsConstructor
public class ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private ProductRepository productRepository;
    private ProductBuilder productBuilder;

    public List<ProductDTO> getAllProducts() {
        LoggerUtility.logEventInfo(LOGGER, GET_ALL_PRODUCTS,IN_PROGRESS,"Fetching all products");

        List<Product> products = productRepository.findAll();

        LoggerUtility.logEventInfo(LOGGER, GET_ALL_PRODUCTS,SUCCESS,String.format("Fetched %d products", products.size()));

        return products.stream()
                .map(productBuilder::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO addProduct(ProductDTO productDTO) {
        LoggerUtility.logEventInfo(LOGGER, ADD_PRODUCT,IN_PROGRESS,"Adding product");

        Product product = productBuilder.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);

        LoggerUtility.logEventInfo(LOGGER, ADD_PRODUCT,SUCCESS, String.format("Product added, id is %d", savedProduct.getId()));

        return productBuilder.toDTO(savedProduct);
    }

    public ProductDTO getProductById(Long id) {
        LoggerUtility.logEventInfo(LOGGER, GET_PRODUCT_BY_ID,IN_PROGRESS,String.format("Fetching product by id %d", id));

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    LoggerUtility.logEventWarn(LOGGER, GET_PRODUCT_BY_ID,FAILURE,String.format("Product with id %d not found", id));

                    return new ResourceNotFoundException("Product with id " + id + " not found");

                });

        LoggerUtility.logEventInfo(LOGGER, GET_PRODUCT_BY_ID,SUCCESS,String.format("Fetched product with id %d", id));

        return productBuilder.toDTO(product);

    }

    public ProductDTO updatePrice(Long productId, ChangePriceRequest newPriceDTO) {
        LoggerUtility.logEventInfo(LOGGER, UPDATE_PRODUCT_PRICE,IN_PROGRESS,String.format("Fetching product with id %d", productId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> {
                    LoggerUtility.logEventWarn(LOGGER, UPDATE_PRODUCT_PRICE,FAILURE,String.format("Product with id %d not found", productId));

                    return new ResourceNotFoundException("Product with id " + productId + " not found");
                });

        product.setPrice(newPriceDTO.getNewPrice());

        Product updatedProduct = productRepository.save(product);

        LoggerUtility.logEventInfo(LOGGER,UPDATE_PRODUCT_PRICE,SUCCESS,"Updated product price");

        return productBuilder.toDTO(updatedProduct);
    }
}
