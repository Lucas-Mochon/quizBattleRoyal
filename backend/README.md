# Quiz Battle Royale - Backend

## Structure

```
backend/
├── eureka-server/          # Service Registry (port 8761)
├── api-gateway/            # API Gateway (port 8080)
├── auth-service/           # Auth & User Management (port 8081)
├── quiz-service/           # Quiz Management (port 8082)
├── websocket-service/      # Real-time Stats & Events (port 8083)
├── docker-compose.yml      # Docker Compose Config
└── pom.xml                 # Parent Maven POM
```

## Architecture

- **Java 21** - Latest LTS version
- **Spring Boot 3.4.1** - Latest stable
- **Spring Cloud 2024.0.0** - Latest release train
- **PostgreSQL 16** - Database
- **Flyway** - Database migrations
- **Eureka** - Service discovery
- **Spring Cloud Gateway** - API Gateway
- **Docker & Docker Compose** - Containerization

## Database

PostgreSQL runs on:

- Internal: `5432`
- External: `5433` (accessible from host)
- Database: `quizbattle`
- User: `postgres` / Password: `postgres`

## Microservices

| Service           | Port | Purpose                                            |
| ----------------- | ---- | -------------------------------------------------- |
| Eureka Server     | 8761 | Service Registry & Discovery                       |
| API Gateway       | 8080 | API Routes & Load Balancing                        |
| Auth Service      | 8081 | User Authentication & Management                   |
| Quiz Service      | 8082 | Quiz, Questions, Sessions, Power-ups, Achievements |
| WebSocket Service | 8083 | Real-time Stats & Events                           |

## Building & Running

### Build all services

```bash
cd backend
mvn clean package -DskipTests
```

### Run with Docker Compose

```bash
docker-compose up -d --build
```

### Check services

```bash
# Eureka Dashboard
http://localhost:8761

# API Gateway Health
http://localhost:8080/actuator/health

# Service Status
curl http://localhost:8761/actuator/metrics
```

## Database Migrations

Migrations run automatically on service startup via Flyway:

- `auth-service`: `V1__Initial_users_schema.sql`
- `quiz-service`: `V1__Initial_quiz_schema.sql`
- `websocket-service`: `V1__Initial_user_stats_schema.sql`

All tables share the same PostgreSQL database `quizbattle`.

## Development

### Local Development (without Docker)

1. Start PostgreSQL locally on port 5432
2. Update `application.yml` datasource URL to `localhost:5432`
3. Run Eureka Server first
4. Run services individually

### Add New Service

1. Create new folder under `backend/`
2. Copy and modify `pom.xml` from existing service
3. Add to parent `pom.xml` modules
4. Create `application.yml` with Eureka client config
5. Add to `docker-compose.yml`
