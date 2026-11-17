# CLAUDE.md

Guidance for Claude Code when working in this repository.

## Overview

Spring Cloud microservices skeleton ("Arquitetura"), used as a base for other projects.
Java 17, Spring Boot 3.3.3, Spring Cloud 2023.0.x, Maven (each service has its own `mvnw`).
Base package: `com.fidelity`.

## Services (`services/`)

| Service | Port | Role |
|---|---|---|
| `config-server` | 8888 | Spring Cloud Config, `native` profile, files in `src/main/resources/configurations/` |
| `discovery-server` | 8761 | Eureka server (`discovery-service.yml`) |
| `gateway` | 8080 | Spring Cloud Gateway, routes `/api/v1/**` to `lb:http://BACKEND-SERVICE` |
| `backend` | 8090 | REST API, currently only `/api/v1/company` (CRUD), H2 on classpath |

Each service's `application.yml` only sets its name and `spring.config.import: optional:configserver:http://localhost:8888`.
Real settings (ports, routes, Eureka) live in `config-server/src/main/resources/configurations/<spring.application.name>.yml`.
Config file names must match `spring.application.name` (e.g. `backend-service`, `gateway-service`, `discovery-service`).

## Startup order

1. `config-server`
2. `discovery-server`
3. `backend`
4. `gateway`

Because the config import is `optional:`, a service started before the config server silently uses defaults (port 8080). Always start config-server first.

## Commands

Run from inside a service directory:

```bash
./mvnw clean verify          # build + tests
./mvnw spring-boot:run       # run the service
./mvnw test -Dtest=ClassName # single test class
```

Quick checks once everything is up:

```bash
curl localhost:8761/                      # Eureka dashboard
curl localhost:8888/backend-service/default   # config served for backend
curl localhost:8090/api/v1/company        # backend directly
curl localhost:8080/api/v1/company        # through the gateway
```

## Backend layering (`services/backend`)

`controller` -> `service` -> (persistence, not implemented yet). DTOs and entities are separate;
`model/dto/converter/*Converter` implements `BaseEntityConverter<Entity, DTO>` for mapping.
New resources should follow the `Company*` pattern: `XEntity extends BaseEntity<Long>`, `XDTO extends BaseDTO<Long>`, `XConverter`, `XService`, `XController` under `/api/v1/x`.

## Conventions

- Code identifiers in English; some comments are in Portuguese, either is fine.
- Use constructor injection (no field `@Autowired`).
- Use `@PathVariable` for path params (never `jakarta.websocket.server.PathParam`).
- Keep Spring Boot and Spring Cloud versions identical across all services.
- Configuration belongs in config-server, not in each service's `application.yml`.

## Known issues (see PLAN.md)

- `discovery-service.yml` `defaultZone` has `/` instead of `:` before the port.
- `CompanyController.getCompanyById` uses the wrong `@PathParam` annotation.
- `CompanyService` is stubbed: no persistence, `getCompanyById` returns null, `deleteCompany` throws.
- springdoc 2.0.2 is too old for Boot 3.3.x.
- `discovery-server` uses Spring Cloud 2023.0.2, the rest 2023.0.3.

## Environment notes

- The GitHub repo is `p4p4l3gu4x/Arquitetura`. Do not commit `.vscode`, `target/`, or `.DS_Store`.
