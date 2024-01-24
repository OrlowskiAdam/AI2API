package com.example.ai2api.service;

import com.example.ai2api.dto.UserCreateDto;
import com.example.ai2api.dto.UserReadDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    UserReadDto createUser(UserCreateDto userCreateDto);
}