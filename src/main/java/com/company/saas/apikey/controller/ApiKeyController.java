package com.company.saas.apikey.controller;

import com.company.saas.apikey.dto.ApiKeyDto;
import com.company.saas.apikey.service.ApiKeyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/apikeys")
@RequiredArgsConstructor
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    @PostMapping
    public ResponseEntity<ApiKeyDto> generate() {
        return ResponseEntity.status(HttpStatus.CREATED).body(apiKeyService.generateApiKey());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiKeyDto> get(@PathVariable UUID id) {
        return ResponseEntity.ok(apiKeyService.getApiKey(id));
    }

    @PostMapping("/{id}/revoke")
    public ResponseEntity<Void> revoke(@PathVariable UUID id) {
        apiKeyService.revokeApiKey(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        apiKeyService.deleteApiKey(id);
        return ResponseEntity.noContent().build();
    }
}
