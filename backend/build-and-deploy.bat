@echo off
REM Quiz Battle Royale - Build and Deploy Script for Windows

setlocal enabledelayedexpansion

echo ================================================
echo Quiz Battle Royale - Backend Build ^& Deploy
echo ================================================
echo.

REM Check if Java is installed
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Java not found. Please install Java 21 or later.
    exit /b 1
)

REM Check if Maven is installed
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo Maven not found. Please install Maven.
    exit /b 1
)

echo Step 1: Building all services (this may take a few minutes)...
mvn clean package -DskipTests -f pom.xml

if %errorlevel% neq 0 (
    echo.
    echo ================================================
    echo ERROR: Build failed!
    echo ================================================
    echo Please check the errors above. Common issues:
    echo  - Java 21 or later not installed
    echo  - Maven not in PATH
    echo  - Network issues downloading dependencies
    echo.
    exit /b 1
)

echo.
echo ================================================
echo ✓ Build successful!
echo ================================================
echo.
echo Step 2: Starting Docker Compose...
echo.

REM Check if Docker is installed
docker -v >nul 2>&1
if %errorlevel% neq 0 (
    echo Docker not found. Please install Docker Desktop.
    exit /b 1
)

REM Stop existing containers
docker-compose down 2>nul

REM Start services
docker-compose up -d --build

if %errorlevel% neq 0 (
    echo.
    echo ================================================
    echo ERROR: Docker Compose failed!
    echo ================================================
    echo.
    exit /b 1
)

echo.
echo ================================================
echo ✓ All services are running!
echo ================================================
echo.
echo Services:
echo   - Eureka Server: http://localhost:8761
echo   - API Gateway: http://localhost:8080
echo   - Auth Service: http://localhost:8081
echo   - Quiz Service: http://localhost:8082
echo   - WebSocket Service: http://localhost:8083
echo   - PostgreSQL: localhost:5433
echo.
echo Check logs with:
echo   docker-compose logs -f [service-name]
echo.
echo Stop services with:
echo   docker-compose down
echo.
