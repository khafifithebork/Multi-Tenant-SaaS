package com.company.saas.role.service;

import com.company.saas.role.dto.CreateRoleRequest;
import com.company.saas.role.dto.RoleDto;
import com.company.saas.role.entity.Role;
import com.company.saas.role.repository.RoleRepository;
import com.company.saas.security.context.TenantContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final TenantContext tenantContext;

    @Transactional
    public RoleDto createRole(CreateRoleRequest request) {
        Role role = new Role();
        role.setTenantId(tenantContext.getTenantId());
        role.setName(request.getName());
        role.setPermissions(request.getPermissions());
        return toDto(roleRepository.save(role));
    }

    @Transactional(readOnly = true)
    public RoleDto getRole(UUID id) {
        return roleRepository.findById(id)
                .map(this::toDto)
                .orElseThrow(() -> new RuntimeException("Role not found: " + id));
    }

    @Transactional
    public RoleDto updateRole(UUID id, CreateRoleRequest request) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found: " + id));
        role.setName(request.getName());
        role.setPermissions(request.getPermissions());
        return toDto(roleRepository.save(role));
    }

    @Transactional
    public void deleteRole(UUID id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found: " + id));
        roleRepository.delete(role);
    }

    private RoleDto toDto(Role role) {
        return RoleDto.builder()
                .id(role.getId())
                .name(role.getName())
                .permissions(role.getPermissions())
                .createdAt(role.getCreatedAt())
                .build();
    }
}
