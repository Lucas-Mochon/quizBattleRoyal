# Quiz Battle Royale - Quick Start Guide

## Prerequisites

Install these on your Windows machine:

- **Java 21+** - https://adoptium.net/ (Eclipse Temurin)
- **Maven 3.9+** - https://maven.apache.org/download.cgi
- **Docker Desktop** - https://www.docker.com/products/docker-desktop/

### Verify Installation

```bash
java -version
mvn -version
docker -v
```

---

## Option 1: One-Click Deploy (Recommended)

### Run the complete build and deploy script:

```bash
cd backend
.\build-and-deploy.bat
```

This will:

1. ✅ Compile all services with Maven (creates JAR files)
2. ✅ Stop any existing Docker containers
3. ✅ Start all services with Docker Compose
4. ✅ Display service URLs

**Time**: ~5-10 minutes on first run (downloading dependencies)

---

## Option 2: Step-by-Step Manual

### Step 1: Build Only (No Docker)

```bash
cd backend
.\build-only.bat
```

This compiles all services and creates JAR files in `target/` folders.

### Step 2: Check Build Status

```bash
.\diagnose.bat
```

Should show ✓ for all services if build successful.

### Step 3: Start Docker Services

```bash
docker-compose up -d --build
```

---

## Option 3: Docker Compose Only (After Build)

If builds are already done:

```bash
cd backend
docker-compose up -d
```

---

## Checking Service Status

### View Services

```bash
docker-compose ps
```

### View Logs

```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f auth-service
docker-compose logs -f postgres
```

### Test Services

```bash
# Eureka Dashboard
http://localhost:8761

# API Gateway Health
http://localhost:8080/actuator/health

# Auth Service
http://localhost:8081

# Quiz Service
http://localhost:8082

# WebSocket Service
http://localhost:8083

# PostgreSQL
localhost:5433
```

---

## Troubleshooting

### Problem: "target: no such file or directory"

**Solution**: Run build first:

```bash
.\build-only.bat
.\build-and-deploy.bat
```

### Problem: "Maven not found"

**Solution**:

- Install Maven from https://maven.apache.org/download.cgi
- Add Maven to PATH (System Environment Variables)

### Problem: "Java version mismatch"

**Solution**:

- Install Java 21 or later
- Check: `java -version` (should show Java 21+)

### Problem: Docker pull timeout

**Solution**:

- Check internet connection
- Restart Docker Desktop
- Clear Docker cache: `docker system prune`

### Problem: Port already in use

**Solution**: Stop all containers first:

```bash
docker-compose down
```

### Problem: Database connection refused

**Solution**: Wait 30 seconds for PostgreSQL to start:

```bash
docker-compose logs -f postgres
```

---

## Useful Commands

### Stop All Services

```bash
docker-compose down
```

### Remove All Data (Fresh Start)

```bash
docker-compose down -v
```

### View Database

```bash
# Connect to PostgreSQL
docker exec -it quiz-postgres psql -U postgres -d quizbattle

# List tables
\dt

# Exit
\q
```

### View Individual Service Logs

```bash
docker-compose logs -f eureka-server
docker-compose logs -f auth-service
docker-compose logs -f quiz-service
docker-compose logs -f websocket-service
docker-compose logs -f postgres
```

### Rebuild Specific Service

```bash
docker-compose up -d --build auth-service
```

---

## Service Architecture

```
┌─────────────────────────────────────────────────┐
│         Frontend (Next.js - port 3000)          │
└────────────────┬────────────────────────────────┘
                 │
┌────────────────▼────────────────────────────────┐
│  API Gateway (Spring Cloud Gateway - 8080)      │
└─┬──────────────┬────────────┬────────────────┬──┘
  │              │            │                │
┌─▼────────┐  ┌─▼────────┐ ┌─▼──────────┐ ┌─▼───────────┐
│   Auth   │  │  Quiz    │ │ WebSocket  │ │   Eureka    │
│ Service  │  │ Service  │ │  Service   │ │   Server    │
│ :8081    │  │ :8082    │ │   :8083    │ │   :8761     │
└─┬────────┘  └─┬────────┘ └─┬──────────┘ └─────────────┘
  │             │             │
  └─────────────┴─────────────┘
                │
        ┌───────▼──────────┐
        │  PostgreSQL 16   │
        │  localhost:5433  │
        └──────────────────┘
```

---

## Environment Variables

All configured automatically in Docker Compose:

- `SPRING_DATASOURCE_URL` - PostgreSQL connection
- `SPRING_DATASOURCE_USERNAME` - postgres
- `SPRING_DATASOURCE_PASSWORD` - postgres
- `EUREKA_CLIENT_SERVICEURL_DEFAULTZONE` - Service discovery
- `SPRING_JPA_HIBERNATE_DDL_AUTO` - validate (no auto schema)

---

## Next Steps

1. ✅ Run `build-and-deploy.bat`
2. ✅ Wait for all services to start (2-3 minutes)
3. ✅ Check Eureka: http://localhost:8761
4. ✅ Start building your API endpoints!

---

## Contact & Support

For issues, check:

- Docker logs: `docker-compose logs [service]`
- Maven logs: Check terminal output
- This README
