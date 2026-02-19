package com.company.saas.tenant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateOrganizationRequest {
    @NotBlank
    private String name;
    private String planType;
}
