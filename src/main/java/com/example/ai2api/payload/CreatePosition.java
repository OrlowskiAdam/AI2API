package com.example.ai2api.payload;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CreatePosition {
    @NotNull(message = "Name cannot be null")
    private String positionName;
    @NotNull(message = "Company ID cannot be null")
    private Long companyId;
}
