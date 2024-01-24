package com.example.ai2api.utils.EmailValidator;

import com.example.ai2api.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
class UserLoginValidator implements ConstraintValidator<UserLogin, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByLogin(login);
    }
}
