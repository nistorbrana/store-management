package com.learning.storemanagement.dto.builder;

import com.learning.storemanagement.dto.UserDTO;
import com.learning.storemanagement.model.StoreUser;
import org.springframework.stereotype.Component;

@Component
public class UserBuilder {

    public StoreUser toEntity(UserDTO dto) {
        return StoreUser.builder()
                .id(dto.getId())
                .password(dto.getPassword())
                .username(dto.getUsername())
                .build();
    }

    public UserDTO toDTO(StoreUser entity) {
        return UserDTO.builder()
                .id(entity.getId())
                .password(entity.getPassword())
                .username(entity.getUsername())
                .build();
    }
}
