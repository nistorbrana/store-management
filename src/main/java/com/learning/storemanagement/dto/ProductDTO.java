package com.learning.storemanagement.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private Long id;
    @NotEmpty(message = "Product name cannot be empty")
    private String name;
    @Positive(message = "Product price must be greater than 0")
    @NotNull(message = "Product price cannot be null")
    private Double price;
    @NotNull(message = "Product stock cannot be null")
    @Min(value = 0, message = "Product stock cannot be negative")
    private Integer stock;
}
