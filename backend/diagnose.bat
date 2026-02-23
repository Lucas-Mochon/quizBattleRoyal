@echo off
REM Quiz Battle Royale - Diagnostics Script

echo ================================================
echo Quiz Battle Royale - Diagnostics
echo ================================================
echo.

echo Checking Java...
java -version
echo.

echo Checking Maven...
mvn -version
echo.

echo Checking Docker...
docker -v
echo.

echo Checking service target folders...
if exist "eureka-server\target\*.jar" (
    echo ✓ eureka-server: JAR found
) else (
    echo ✗ eureka-server: NO JAR found (needs compilation)
)

if exist "api-gateway\target\*.jar" (
    echo ✓ api-gateway: JAR found
) else (
    echo ✗ api-gateway: NO JAR found (needs compilation)
)

if exist "auth-service\target\*.jar" (
    echo ✓ auth-service: JAR found
) else (
    echo ✗ auth-service: NO JAR found (needs compilation)
)

if exist "quiz-service\target\*.jar" (
    echo ✓ quiz-service: JAR found
) else (
    echo ✗ quiz-service: NO JAR found (needs compilation)
)

if exist "websocket-service\target\*.jar" (
    echo ✓ websocket-service: JAR found
) else (
    echo ✗ websocket-service: NO JAR found (needs compilation)
)

echo.
echo ================================================
echo To fix: Run build-and-deploy.bat
echo ================================================
