package com.company.saas.role.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateRoleRequest {
    @NotBlank
    private String name;

    @NotBlank
    private String permissions;
}
