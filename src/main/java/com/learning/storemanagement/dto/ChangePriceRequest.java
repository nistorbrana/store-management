package com.learning.storemanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePriceRequest {

    @NotNull(message = "New price cannot be null")
    @Positive(message = "New price must be greater than 0")
    private Double newPrice;
}
