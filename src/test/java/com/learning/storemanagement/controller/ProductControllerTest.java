package com.learning.storemanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learning.storemanagement.dto.ProductDTO;
import com.learning.storemanagement.exception.ResourceNotFoundException;
import com.learning.storemanagement.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    private static final String ENDPOINT = "/products";
    private static final String PRODUCT_NAME = "Product Name";
    private static final Double PRODUCT_PRICE = 12.0;
    private static final Integer PRODUCT_STOCK = 1;
    private static final String PRODUCT_NAME_2 = "Product 2 Name";
    private static final Double PRODUCT_PRICE_2 = 22.0;
    private static final Integer PRODUCT_STOCK_2 = 3;
    private static final String INVALID_PRODUCT_NAME = "Product name cannot be empty";
    private static final String INVALID_PRODUCT_PRICE = "Product price must be greater than 0";
    private static final String INVALID_PRODUCT_STOCK = "Product stock cannot be negative";

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private ProductService productService;

    @Test
    public void getAllProducts() throws Exception {
        when(productService.getAllProducts()).thenReturn(givenProductsDTO());

        mockMvc.perform(get(ENDPOINT))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$[0].price").value(PRODUCT_PRICE))
                .andExpect(jsonPath("$[0].stock").value(PRODUCT_STOCK))
                .andExpect(jsonPath("$[1].name").value(PRODUCT_NAME_2))
                .andExpect(jsonPath("$[1].price").value(PRODUCT_PRICE_2))
                .andExpect(jsonPath("$[1].stock").value(PRODUCT_STOCK_2));
    }

    @Test
    public void getProductById_success() throws Exception {
        when(productService.getProductById(1L)).thenReturn(givenProductDTO(1L, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK));

        mockMvc.perform(get(ENDPOINT + "/" + 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(PRODUCT_NAME))
                .andExpect(jsonPath("$.price").value(PRODUCT_PRICE))
                .andExpect(jsonPath("$.stock").value(PRODUCT_STOCK));

    }

    @Test
    public void getProductById_notFound() throws Exception {
        when(productService.getProductById(1L)).thenThrow(new ResourceNotFoundException("Product with id 1 not found"));

        mockMvc.perform(get(ENDPOINT + "/" + 1L))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not found"))
                .andExpect(jsonPath("$.message").value("Product with id 1 not found"));
    }

    @Test
    public void addProduct_success() throws Exception {
        String json = objectMapper.writeValueAsString(givenProductDTO(null, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK));

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void addProduct_fieldsNotValid() throws Exception {
        String json = objectMapper.writeValueAsString(givenProductDTO(null, "", -2D, -1));

        mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.name", containsString(INVALID_PRODUCT_NAME)))
                .andExpect(jsonPath("$.price", containsString(INVALID_PRODUCT_PRICE)))
                .andExpect(jsonPath("$.stock", containsString(INVALID_PRODUCT_STOCK)));
    }

    private ProductDTO givenProductDTO(Long id, String name, Double price, Integer stock) {
        return new ProductDTO(id, name, price, stock);
    }

    private List<ProductDTO> givenProductsDTO() {
        return List.of(
                givenProductDTO(1L, PRODUCT_NAME, PRODUCT_PRICE, PRODUCT_STOCK),
                givenProductDTO(2L, PRODUCT_NAME_2, PRODUCT_PRICE_2, PRODUCT_STOCK_2)
        );
    }
}