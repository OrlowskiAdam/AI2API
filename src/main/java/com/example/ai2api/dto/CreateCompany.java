package com.example.ai2api.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreateCompany {
    @NotNull(message = "Company name is required")
    private String name;
}
