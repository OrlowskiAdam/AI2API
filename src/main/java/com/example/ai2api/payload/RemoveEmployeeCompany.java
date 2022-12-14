package com.example.ai2api.payload;

import lombok.Getter;

@Getter
public class RemoveEmployeeCompany {
    private Long employeeId;
    private Long companyId;
}
