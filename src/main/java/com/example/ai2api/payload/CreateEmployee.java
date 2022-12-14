package com.example.ai2api.payload;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
public class CreateEmployee {
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Surname cannot be null")
    private String surname;
    @NotNull(message = "Pesel cannot be null")
    private Long pesel;
    @NotNull(message = "Companies cannot be null")
    private Long companyId;
    @NotNull(message = "Company positions cannot be null")
    private Set<Long> positionIds;
}
