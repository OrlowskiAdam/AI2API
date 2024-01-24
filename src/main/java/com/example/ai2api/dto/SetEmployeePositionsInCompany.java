package com.example.ai2api.dto;

import lombok.Getter;

import java.util.Set;

@Getter
public class SetEmployeePositionsInCompany {
    private Long employeeId;
    private Long companyId;
    private Set<Long> positionIds;
}
