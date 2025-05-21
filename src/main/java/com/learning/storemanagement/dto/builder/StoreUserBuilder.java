package com.learning.storemanagement.dto.builder;

import com.learning.storemanagement.dto.StoreUserDTO;
import com.learning.storemanagement.model.StoreUser;
import org.springframework.stereotype.Component;

@Component
public class StoreUserBuilder {

    public StoreUser toEntity(StoreUserDTO dto) {
        return StoreUser.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .build();
    }

    public StoreUserDTO toDTO(StoreUser entity) {
        return StoreUserDTO.builder()
                .id(entity.getId())
                .password(entity.getPassword())
                .username(entity.getUsername())
                .build();
    }
}
