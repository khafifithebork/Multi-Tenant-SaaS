package com.company.saas.tenant.service;

import com.company.saas.tenant.dto.CreateOrganizationRequest;
import com.company.saas.tenant.dto.OrganizationDto;
import com.company.saas.tenant.entity.Organization;
import com.company.saas.tenant.repository.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Transactional
    public OrganizationDto createOrganization(CreateOrganizationRequest request) {
        Organization org = new Organization();
        org.setName(request.getName());
        if (request.getPlanType() != null) {
            org.setPlanType(request.getPlanType());
        }
        return toDto(organizationRepository.save(org));
    }

    @Transactional(readOnly = true)
    public OrganizationDto getOrganization(UUID id) {
        return organizationRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Organization not found: " + id));
    }

    @Transactional
    public OrganizationDto updateOrganization(UUID id, CreateOrganizationRequest request) {
        Organization org = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found: " + id));
        org.setName(request.getName());
        if (request.getPlanType() != null) {
            org.setPlanType(request.getPlanType());
        }
        return toDto(organizationRepository.save(org));
    }

    @Transactional
    public void deleteOrganization(UUID id) {
        Organization org = organizationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Organization not found: " + id));
        organizationRepository.delete(org);
    }

    private OrganizationDto toDto(Organization org) {
        return OrganizationDto.builder()
                .id(org.getId())
                .name(org.getName())
                .planType(org.getPlanType())
                .status(org.getStatus())
                .createdAt(org.getCreatedAt())
                .build();
    }
}
