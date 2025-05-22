package com.learning.storemanagement.controller;

import com.learning.storemanagement.dto.UserDTO;
import com.learning.storemanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {

    private UserService storeUserService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return new ResponseEntity<>(storeUserService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO storeUserDTO) {
        return new ResponseEntity<>(storeUserService.save(storeUserDTO), HttpStatus.CREATED);
    }
}
