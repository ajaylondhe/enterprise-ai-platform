# Enterprise AI Platform

Medium-to-high complexity enterprise Java project using Java 17, Spring Boot,
Spring AI, Maven, Hibernate/JPA, MySQL, Spring Security/JWT, Spring Cloud,
Docker, OpenAPI, JUnit and Mockito.

## Business Use Case

An enterprise employee knowledge and assistant platform where employees can
authenticate, manage employee/leave data, upload company documents, ask
questions about policies, use RAG, invoke business tools through AI, and
generate document summaries/quizzes.

## Microservices

- common-library
- config-server
- discovery-server
- api-gateway
- auth-service
- employee-service
- leave-service
- document-service
- ai-service
- notification-service

## Local Infrastructure

Docker Compose starts MySQL 8.4 and PostgreSQL 17 with pgvector.
Application services are added in later parts.

## Build

```bash
mvn clean verify
```

## Infrastructure

```bash
docker compose up -d
docker compose down
```

## Roadmap

1. Foundation and Maven multi-module setup
2. Config Server
3. Eureka Discovery Server
4. API Gateway
5. Authentication + JWT
6. Employee Service
7. Leave Service
8. Document Service
9. Spring AI ChatClient
10. Embeddings + Vector Store
11. RAG
12. Conversation memory
13. AI tool/function calling
14. AI summaries and structured output
15. Guardrails and security
16. Validation, exception handling and logging
17. Unit/integration tests
18. OpenAPI
19. Dockerized application
20. CI/CD and production hardening
