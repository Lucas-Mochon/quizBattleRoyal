# Script pour rebuild un service spécifique après modification
# Usage: .\rebuild-service.ps1 <service-name>
# Example: .\rebuild-service.ps1 auth-service

param(
    [Parameter(Mandatory=$true)]
    [ValidateSet('auth-service', 'quiz-service', 'websocket-service', 'api-gateway', 'eureka-server')]
    [string]$ServiceName
)

Write-Host "🔨 Rebuilding $ServiceName..." -ForegroundColor Cyan

# Build avec Maven
Write-Host "📦 Building JAR with Maven..." -ForegroundColor Yellow
Set-Location "backend\$ServiceName"
mvn clean package -DskipTests -q
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Maven build failed!" -ForegroundColor Red
    Set-Location ..\..
    exit 1
}
Set-Location ..\..

# Rebuild et restart le container Docker
Write-Host "🐳 Rebuilding Docker container..." -ForegroundColor Yellow
docker compose -f backend\docker-compose.yml up -d --build --no-deps $ServiceName

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ $ServiceName rebuilt and restarted successfully!" -ForegroundColor Green
    Write-Host "📋 Logs: docker logs quiz-$ServiceName -f" -ForegroundColor Blue
} else {
    Write-Host "❌ Docker rebuild failed!" -ForegroundColor Red
    exit 1
}
