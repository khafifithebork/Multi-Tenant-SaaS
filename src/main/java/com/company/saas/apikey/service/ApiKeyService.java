package com.company.saas.apikey.service;

import com.company.saas.apikey.dto.ApiKeyDto;
import com.company.saas.apikey.entity.ApiKey;
import com.company.saas.apikey.repository.ApiKeyRepository;
import com.company.saas.security.context.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final TenantContext tenantContext;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public ApiKeyDto generateApiKey() {
        String rawKey = "sk_live_" + UUID.randomUUID().toString().replace("-", "");
        ApiKey apiKey = new ApiKey();
        apiKey.setTenantId(tenantContext.getTenantId());
        apiKey.setHashedKey(passwordEncoder.encode(rawKey));
        ApiKey saved = apiKeyRepository.save(apiKey);
        return ApiKeyDto.builder()
                .id(saved.getId())
                .createdAt(saved.getCreatedAt())
                .revokedAt(saved.getRevokedAt())
                .rawKey(rawKey)
                .build();
    }

    @Transactional(readOnly = true)
    public ApiKeyDto getApiKey(UUID id) {
        return apiKeyRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("API key not found: " + id));
    }

    @Transactional
    public void revokeApiKey(UUID id) {
        ApiKey apiKey = apiKeyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("API key not found: " + id));
        apiKey.setRevokedAt(LocalDateTime.now());
        apiKeyRepository.save(apiKey);
    }

    @Transactional
    public void deleteApiKey(UUID id) {
        ApiKey apiKey = apiKeyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("API key not found: " + id));
        apiKeyRepository.delete(apiKey);
    }

    private ApiKeyDto toDto(ApiKey apiKey) {
        return ApiKeyDto.builder()
                .id(apiKey.getId())
                .createdAt(apiKey.getCreatedAt())
                .revokedAt(apiKey.getRevokedAt())
                .build();
    }
}
