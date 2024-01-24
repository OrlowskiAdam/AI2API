package com.example.ai2api.service;

import com.example.ai2api.payload.UserCreateDto;
import com.example.ai2api.payload.UserReadDto;
import com.example.ai2api.model.Role;
import com.example.ai2api.model.User;
import com.example.ai2api.repository.RoleRepository;
import com.example.ai2api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    public UserReadDto createUser(UserCreateDto userCreateDto) {
        char[] encodedPassword = passwordEncoder.encode(userCreateDto.password()).toCharArray();
        Role userRole = roleRepository.findByName("ADMIN").orElseThrow();
        User registerUser = userRepository.save(new User(userCreateDto.email(), userCreateDto.login(), encodedPassword, userRole));
        return UserReadDto.of(registerUser);
    }
}
