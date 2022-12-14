package com.example.ai2api.payload;

import lombok.Getter;

import java.util.Set;

@Getter
public class SetEmployeeCompany {
    private Long employeeId;
    private Long companyId;
    private Set<Long> positionIds;
}
