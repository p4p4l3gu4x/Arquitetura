# PLAN.md - Proposed changes

Ordered by priority. Each phase should be a separate commit/PR.

## Phase 1 - Bug fixes (small, low risk)
1. `discovery-service.yml`: fix `defaultZone` to `http://${eureka.instance.hostname}:${server.port}/eureka/`.
2. `CompanyController.getCompanyById`: replace `@PathParam` with `@PathVariable("companyId")`.
3. Align Spring Cloud to `2023.0.3` in `discovery-server`.
4. Upgrade springdoc to a Boot 3.3-compatible version (2.6.0).
5. Gateway: remove the commented route; choose either explicit routes or the discovery locator, not both.
6. Controller: return a real `Location` header on create (`/api/v1/company/{id}`), drop `throws Exception`.

## Phase 2 - Make the backend real
1. Add `spring-boot-starter-data-jpa`; map `CompanyEntity` (`@Entity`, `@Id @GeneratedValue`); add `CompanyRepository`.
2. Implement `CompanyService` against the repository; throw `CompanyNotFoundException` / `CompanyAlreadyExistsException`.
3. `@RestControllerAdvice` returning RFC 7807 `ProblemDetail` (404, 409, 400).
4. Bean Validation on `CompanyDTO` (`@NotBlank` name, CNPJ format/check digits, unique).
5. Unit tests (service, converter), `@WebMvcTest` (controller), `@DataJpaTest` (repository).

## Phase 3 - Structure and tooling
1. Root parent POM (`packaging=pom`, modules, `dependencyManagement`) so versions live in one place.
2. Upgrade Spring Boot 3.3.x to a supported 3.5.x line, with matching Spring Cloud release train.
3. Actuator on all services; health checks exposed.
4. `fail-fast` + retry for config import (replace `optional:` for non-local profiles).
5. Dockerfile per service + `docker-compose.yml` with startup ordering via healthchecks.
6. GitHub Actions CI: build + test on PR.
7. README (architecture diagram, how to run).

## Phase 4 - Hardening (later)
- Move config to a Git-backed config repository; encrypt secrets.
- Security at the gateway (JWT / OAuth2 resource server).
- Resilience4j circuit breaker + timeouts on gateway routes.
- Observability: Micrometer + tracing.
- Replace H2 with PostgreSQL for non-test profiles, Flyway migrations.

## Verification
- `./mvnw clean verify` passes for every service.
- Smoke test: start in order, `curl` through gateway returns company CRUD results, Eureka shows BACKEND-SERVICE and GATEWAY-SERVICE as UP.
