package com.company.saas.config;

import com.company.saas.security.context.TenantContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class JpaConfig implements WebMvcConfigurer {

    private final TenantContext tenantContext;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request,
                                     HttpServletResponse response,
                                     Object handler) {
                if (tenantContext.isSet()) {
                    Session session = entityManager.unwrap(Session.class);
                    session.enableFilter("tenantFilter")
                           .setParameter("tenantId", tenantContext.getTenantId());
                }
                return true;
            }
        });
    }
}
