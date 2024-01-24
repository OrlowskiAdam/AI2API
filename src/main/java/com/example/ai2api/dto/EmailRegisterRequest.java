package com.example.ai2api.dto;

import com.example.ai2api.model.User;

public record EmailRegisterRequest(String email, String login) {

    public static EmailRegisterRequest of(User user) {
        return new EmailRegisterRequest(
                user.getEmail(),
                user.getLogin()
        );
    }
}