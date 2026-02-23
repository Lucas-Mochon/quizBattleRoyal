@echo off
REM Quiz Battle Royale - Build Only Script (No Docker)

setlocal enabledelayedexpansion

echo ================================================
echo Quiz Battle Royale - Build Services Only
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

echo Compiling all services with Maven...
echo This may take several minutes on first run.
echo.

mvn clean package -DskipTests -f pom.xml

if %errorlevel% neq 0 (
    echo.
    echo ================================================
    echo ERROR: Build failed!
    echo ================================================
    echo.
    echo Common issues:
    echo  - Java 21 or later not installed
    echo  - Maven not in PATH environment
    echo  - Network issues downloading Maven dependencies
    echo  - Insufficient disk space
    echo.
    echo Solutions:
    echo  - Check internet connection
    echo  - Run: mvn clean
    echo  - Try again: mvn package -DskipTests
    echo.
    exit /b 1
)

echo.
echo ================================================
echo ✓ Build successful!
echo ================================================
echo.
echo Next steps:
echo  1. Run: build-and-deploy.bat (for Docker)
echo  2. Or: docker-compose up -d --build
echo.
