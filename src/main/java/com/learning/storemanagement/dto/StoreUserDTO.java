package com.learning.storemanagement.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreUserDTO {
    private Long id;
    private String username;
    private String password;
}
