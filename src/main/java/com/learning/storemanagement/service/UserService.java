package com.learning.storemanagement.service;

import com.learning.storemanagement.dto.UserDTO;
import com.learning.storemanagement.dto.builder.UserBuilder;
import com.learning.storemanagement.model.StoreUser;
import com.learning.storemanagement.repository.StoreUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private StoreUserRepository storeUserRepository;
    private UserBuilder storeUserBuilder;
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> findAll() {
        List<StoreUser> users = storeUserRepository.findAll();
        return users.stream()
                .map(storeUserBuilder::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO save(UserDTO userDTO) {
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        StoreUser storeUser = storeUserBuilder.toEntity(userDTO);
        storeUser.setPassword(encodedPassword);

        return storeUserBuilder.toDTO(storeUserRepository.save(storeUser));
    }
}
