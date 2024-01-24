package com.example.ai2api.service.impl;

import com.example.ai2api.dto.UserCreateDto;
import com.example.ai2api.dto.UserReadDto;
import com.example.ai2api.model.Role;
import com.example.ai2api.model.User;
import com.example.ai2api.repository.RoleRepository;
import com.example.ai2api.repository.UserRepository;
import com.example.ai2api.service.EmailRegisterService;
import com.example.ai2api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.ai2api.dto.EmailRegisterRequest.of;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmailRegisterService emailRegisterService;

    public UserReadDto createUser(UserCreateDto userCreateDto) {
        char[] encodedPassword = passwordEncoder.encode(userCreateDto.password()).toCharArray();
        Role userRole = roleRepository.findByName("ADMIN").orElseThrow();
        User registerUser = userRepository.save(new User(userCreateDto.email(), userCreateDto.login(), encodedPassword, userRole));
        emailRegisterService.sendConfirmationEmail(of(registerUser));
        return UserReadDto.of(registerUser);
    }
}
