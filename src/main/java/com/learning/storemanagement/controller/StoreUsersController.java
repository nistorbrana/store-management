package com.learning.storemanagement.controller;

import com.learning.storemanagement.dto.StoreUserDTO;
import com.learning.storemanagement.service.StoreUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class StoreUsersController {

    private StoreUserService storeUserService;

    @GetMapping
    public ResponseEntity<List<StoreUserDTO>> getAllUsers() {
        return new ResponseEntity<>(storeUserService.findAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<StoreUserDTO> createUser(@RequestBody StoreUserDTO storeUserDTO) {
        return new ResponseEntity<>(storeUserService.save(storeUserDTO), HttpStatus.CREATED);
    }
}
