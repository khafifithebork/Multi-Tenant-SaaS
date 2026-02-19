# Multi-Tenant SaaS Management Platform

A production-grade, scalable backend built with Java 21, Spring Boot 3.2, PostgreSQL, Hibernate Filters for tenant isolation, and JWT-based stateless authentication.

## Tech Stack

- **Java 21**
- **Spring Boot 3.2**
- **Spring Security** (JWT-based stateless auth)
- **Spring Data JPA** + **Hibernate 6** (tenant isolation via Hibernate Filters)
- **PostgreSQL 15**
- **JJWT 0.12.5**
- **Lombok**
- **Docker / Docker Compose**

## Prerequisites

- Java 21+
- Maven 3.9+
- Docker & Docker Compose

## Running with Docker Compose

```bash
docker compose up --build
```

The application will be available at `http://localhost:8080`.

## Running Locally

1. Start PostgreSQL (or configure an existing instance):
```bash
docker compose up postgres
```

2. Build and run the application:
```bash
mvn spring-boot:run
```

## API Endpoints

| Method | Endpoint | Auth Required | Description |
|--------|----------|--------------|-------------|
| POST | /api/v1/organizations | No | Create organization |
| GET | /api/v1/organizations/{id} | No | Get organization |
| PUT | /api/v1/organizations/{id} | No | Update organization |
| DELETE | /api/v1/organizations/{id} | No | Delete organization |
| POST | /api/v1/users | Yes | Create user |
| GET | /api/v1/users/{id} | Yes | Get user |
| PUT | /api/v1/users/{id} | Yes | Update user |
| DELETE | /api/v1/users/{id} | Yes | Delete user |
| POST | /api/v1/roles | Yes | Create role |
| GET | /api/v1/roles/{id} | Yes | Get role |
| PUT | /api/v1/roles/{id} | Yes | Update role |
| DELETE | /api/v1/roles/{id} | Yes | Delete role |
| POST | /api/v1/apikeys | Yes | Generate API key |
| GET | /api/v1/apikeys/{id} | Yes | Get API key info |
| POST | /api/v1/apikeys/{id}/revoke | Yes | Revoke API key |
| DELETE | /api/v1/apikeys/{id} | Yes | Delete API key |

## JWT Authentication

Include a JWT token in requests:
```
Authorization: Bearer <token>
```

The JWT token must contain:
- `sub`: User UUID
- `tenant_id`: Tenant (Organization) UUID
- `role`: User's role

## Architecture

```
┌─────────────────────────────────────┐
│           HTTP Request              │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│      JwtAuthenticationFilter        │
│  (extracts JWT, sets TenantContext) │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│       TenantFilterInterceptor       │
│  (enables Hibernate tenantFilter)   │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│          Controllers                │
│    (OrganizationController, etc.)   │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│           Services                  │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│        Repositories (JPA)           │
│  (auto-filtered by tenantId)        │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│          PostgreSQL                  │
└─────────────────────────────────────┘
```

## Tenant Isolation

All tenant-scoped entities (User, Role, ApiKey) are protected by a Hibernate Filter named `tenantFilter`. The filter is enabled per-request using the `tenantId` extracted from the JWT token.
