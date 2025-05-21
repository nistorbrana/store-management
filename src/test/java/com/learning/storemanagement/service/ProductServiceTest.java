package com.learning.storemanagement.service;

import com.learning.storemanagement.dto.ChangePriceRequest;
import com.learning.storemanagement.dto.ProductDTO;
import com.learning.storemanagement.dto.builder.ProductBuilder;
import com.learning.storemanagement.exception.ResourceNotFoundException;
import com.learning.storemanagement.model.Product;
import com.learning.storemanagement.repository.ProductRepository;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ProductServiceTest {

    private static final String PRODUCT_NAME = "Product Name";
    private static final Double PRODUCT_PRICE = 12.0;
    private static final Integer PRODUCT_STOCK = 1;
    private static final String PRODUCT_NAME_2 = "Product 2 Name";
    private static final Double PRODUCT_PRICE_2 = 22.0;
    private static final Integer PRODUCT_STOCK_2 = 3;
    private static final Double NEW_PRICE = 50.0;

    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductBuilder productBuilder;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    public void getAllProducts() {
        List<Product> products = givenProducts();
        List<ProductDTO> productDTOs = givenProductsDTO();

        when(productRepository.findAll()).thenReturn(products);
        whenToDTOIsCalled(products.get(0), productDTOs.get(0));
        whenToDTOIsCalled(products.get(1), productDTOs.get(1));

        List<ProductDTO> result = productService.getAllProducts();

        assertEquals(2, result.size());
        assertEquals(PRODUCT_NAME, result.get(0).getName());
        assertEquals(PRODUCT_PRICE, result.get(0).getPrice());
        assertEquals(PRODUCT_STOCK, result.get(0).getStock());
        assertEquals(PRODUCT_NAME_2, result.get(1).getName());
        assertEquals(PRODUCT_STOCK_2, result.get(1).getStock());
        assertEquals(PRODUCT_PRICE_2, result.get(1).getPrice());
    }

    @Test
    public void addProduct() {
        ProductDTO receivedDTO = givenProductDTO(null, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK);
        Product productToSave = givenProduct(null, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK);

        Product savedProduct = givenProduct(1L, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK);
        ProductDTO expectedDTO = givenProductDTO(1L, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK);

        whenToEntityIsCalled(productToSave, receivedDTO);
        when(productRepository.save(productToSave)).thenReturn(savedProduct);
        whenToDTOIsCalled(savedProduct, expectedDTO);

        ProductDTO result = productService.addProduct(receivedDTO);

        assertEquals(PRODUCT_NAME, result.getName());
        assertEquals(PRODUCT_PRICE, result.getPrice());
        assertEquals(PRODUCT_STOCK, result.getStock());
        assertEquals(1L, result.getId());
    }

    @Test
    public void getProductById_notFound() {
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(any()));
    }

    @Test
    public void getProductById_found() {
        Product product = givenProduct(1L, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK);
        ProductDTO productDTO = givenProductDTO(1L, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        whenToDTOIsCalled(product, productDTO);

        ProductDTO result = productService.getProductById(1L);

        assertEquals(PRODUCT_NAME, result.getName());
        assertEquals(PRODUCT_PRICE, result.getPrice());
        assertEquals(PRODUCT_STOCK, result.getStock());
        assertEquals(1L, result.getId());
    }

    @Test
    public void updatePrice_success() {
        Product product = givenProduct(1L, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK);
        Product updatedProduct = givenProduct(1L, PRODUCT_NAME, NEW_PRICE, PRODUCT_STOCK);
        ProductDTO productDTO = givenProductDTO(1L, PRODUCT_NAME, NEW_PRICE, PRODUCT_STOCK);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(updatedProduct);
        whenToDTOIsCalled(updatedProduct, productDTO);

        ProductDTO result = productService.updatePrice(1L,givenChangePriceRequest());

        assertEquals(NEW_PRICE, result.getPrice());
        assertEquals(PRODUCT_STOCK, result.getStock());
        assertEquals(PRODUCT_NAME, result.getName());
        assertEquals(1L, result.getId());
    }

    @Test
    public void updatePrice_productNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> productService.updatePrice(1L,any()));
    }

    private List<Product> givenProducts() {
        return List.of(
                givenProduct(1L, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK),
                givenProduct(2L, PRODUCT_NAME_2, PRODUCT_PRICE_2, PRODUCT_STOCK_2)
        );
    }

    private Product givenProduct(Long id, String name, Double price, Integer stock) {
        return new Product(id, name, price, stock);
    }

    private List<ProductDTO> givenProductsDTO() {
        return List.of(
                givenProductDTO(1L, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK),
                givenProductDTO(2L, PRODUCT_NAME_2, PRODUCT_PRICE_2, PRODUCT_STOCK_2)
        );
    }

    private ProductDTO givenProductDTO(Long id, String name, Double price, Integer stock) {
        return new ProductDTO(id, name, price, stock);
    }

    private ChangePriceRequest givenChangePriceRequest() {
        return new ChangePriceRequest(50.0);
    }

    private void whenToDTOIsCalled(Product product, ProductDTO productDTO) {
        when(productBuilder.toDTO(product)).thenReturn(productDTO);
    }

    private void whenToEntityIsCalled(Product product, ProductDTO productDTO) {
        when(productBuilder.toEntity(productDTO)).thenReturn(product);
    }
}