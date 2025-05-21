package com.learning.storemanagement.service;

import com.learning.storemanagement.dto.StoreUserDTO;
import com.learning.storemanagement.dto.builder.StoreUserBuilder;
import com.learning.storemanagement.model.StoreUser;
import com.learning.storemanagement.repository.StoreUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StoreUserService {

    private StoreUserRepository storeUserRepository;
    private StoreUserBuilder storeUserBuilder;

    public List<StoreUserDTO> findAll() {
        List<StoreUser> users = storeUserRepository.findAll();
        return users.stream()
                .map(storeUserBuilder::toDTO)
                .collect(Collectors.toList());
    }

    public StoreUserDTO save(StoreUserDTO storeUserDTO) {
        StoreUser storeUser = storeUserBuilder.toEntity(storeUserDTO);
        return storeUserBuilder.toDTO(storeUserRepository.save(storeUser));
    }
}
