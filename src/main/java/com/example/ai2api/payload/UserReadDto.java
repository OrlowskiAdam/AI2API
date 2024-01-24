package com.example.ai2api.payload;

import com.example.ai2api.model.User;

public record UserReadDto(String login, String email) {

    public static UserReadDto of(User user) {
        return new UserReadDto(user.getLogin(), user.getEmail());
    }
}
