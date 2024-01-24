package com.example.ai2api.controllers;

import com.example.ai2api.dto.UserCreateDto;
import com.example.ai2api.dto.UserReadDto;
import com.example.ai2api.configuration.security.LoginCredentials;
import com.example.ai2api.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @PostMapping("/register")
    public ResponseEntity<UserReadDto> register(
            @Valid @RequestBody UserCreateDto userCreateDto) {
        return new ResponseEntity<>(userServiceImpl.createUser(userCreateDto), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public void login(@RequestBody LoginCredentials credentials) {
    }
}
