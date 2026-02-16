package com.company.saas.security.context;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import java.util.UUID;

@Component
@RequestScope
public class TenantContext {
    private UUID tenantId;
    private UUID userId;

    public void setTenantId(UUID tenantId) {
        this.tenantId = tenantId;
    }

    public UUID getTenantId() {
        if (tenantId == null) {
            throw new IllegalStateException("Tenant context not set");
        }
        return tenantId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getUserId() {
        if (userId == null) {
            throw new IllegalStateException("User context not set");
        }
        return userId;
    }

    public boolean isSet() {
        return tenantId != null && userId != null;
    }
}