# Build all services
Write-Host "Building all services..." -ForegroundColor Green

# Build Eureka Server
Write-Host "`nBuilding Eureka Server..." -ForegroundColor Cyan
Set-Location eureka-server
mvn clean package -DskipTests
Set-Location ..

# Build API Gateway
Write-Host "`nBuilding API Gateway..." -ForegroundColor Cyan
Set-Location api-gateway
mvn clean package -DskipTests
Set-Location ..

# Build Auth Service
Write-Host "`nBuilding Auth Service..." -ForegroundColor Cyan
Set-Location auth-service
mvn clean package -DskipTests
Set-Location ..

# Build Quiz Service
Write-Host "`nBuilding Quiz Service..." -ForegroundColor Cyan
Set-Location quiz-service
mvn clean package -DskipTests
Set-Location ..

# Build WebSocket Service
Write-Host "`nBuilding WebSocket Service..." -ForegroundColor Cyan
Set-Location websocket-service
mvn clean package -DskipTests
Set-Location ..

Write-Host "`nAll services built successfully!" -ForegroundColor Green
Write-Host "Run 'docker-compose up' to start all services" -ForegroundColor Yellow
