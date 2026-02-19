package com.company.saas.apikey.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiKeyDto {
    private UUID id;
    private LocalDateTime createdAt;
    private LocalDateTime revokedAt;
    private String rawKey;
}
