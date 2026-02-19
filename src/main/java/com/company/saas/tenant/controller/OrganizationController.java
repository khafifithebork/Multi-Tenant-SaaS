package com.company.saas.tenant.controller;

import com.company.saas.tenant.dto.CreateOrganizationRequest;
import com.company.saas.tenant.dto.OrganizationDto;
import com.company.saas.tenant.service.OrganizationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/organizations")
@RequiredArgsConstructor
public class OrganizationController {

    private final OrganizationService organizationService;

    @PostMapping
    public ResponseEntity<OrganizationDto> create(@Valid @RequestBody CreateOrganizationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(organizationService.createOrganization(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> get(@PathVariable UUID id) {
        return ResponseEntity.ok(organizationService.getOrganization(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrganizationDto> update(@PathVariable UUID id,
                                                   @Valid @RequestBody CreateOrganizationRequest request) {
        return ResponseEntity.ok(organizationService.updateOrganization(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        organizationService.deleteOrganization(id);
        return ResponseEntity.noContent().build();
    }
}
