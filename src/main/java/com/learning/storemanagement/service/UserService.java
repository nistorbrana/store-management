package com.learning.storemanagement.service;

import com.learning.storemanagement.dto.UserDTO;
import com.learning.storemanagement.dto.builder.UserBuilder;
import com.learning.storemanagement.exception.UsernameAlreadyExistsException;
import com.learning.storemanagement.model.StoreUser;
import com.learning.storemanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private UserBuilder userBuilder;
    private PasswordEncoder passwordEncoder;

    public List<UserDTO> findAll() {
        List<StoreUser> users = userRepository.findAll();
        return users.stream()
                .map(userBuilder::toDTO)
                .collect(Collectors.toList());
    }

    public UserDTO save(UserDTO userDTO) {
        String username = userDTO.getUsername();
        if(userRepository.findByUsername(username).isPresent()) {
            throw new UsernameAlreadyExistsException("Username " + username + " already exists");
        }
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        StoreUser user = userBuilder.toEntity(userDTO);
        user.setPassword(encodedPassword);

        return userBuilder.toDTO(userRepository.save(user));
    }
}
