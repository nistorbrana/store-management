package com.learning.storemanagement.controller;

import com.learning.storemanagement.dto.ProductDTO;
import com.learning.storemanagement.dto.ChangePriceRequest;
import com.learning.storemanagement.service.ProductService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDTO> addProduct(@Valid @RequestBody ProductDTO productDTO) {
        ProductDTO addedProduct = productService.addProduct(productDTO);
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("{productId}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long productId) {
        ProductDTO productDTO = productService.getProductById(productId);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

    @PatchMapping({"{productId}/price"})
    public ResponseEntity<ProductDTO> updateProductPrice(@PathVariable Long productId, @Valid @RequestBody ChangePriceRequest newPriceDTO) {
        ProductDTO productDTO = productService.updatePrice(productId, newPriceDTO);
        return new ResponseEntity<>(productDTO, HttpStatus.OK);
    }

}
