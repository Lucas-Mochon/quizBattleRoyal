#!/bin/bash

# Quiz Battle Royale - Build and Deploy Script

set -e

echo "================================================"
echo "Quiz Battle Royale - Backend Build & Deploy"
echo "================================================"
echo ""

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Check if Maven is installed
if ! command -v mvn &> /dev/null; then
    echo -e "${RED}Maven not found. Please install Maven.${NC}"
    exit 1
fi

echo -e "${YELLOW}Step 1: Building all services...${NC}"
mvn clean package -DskipTests -f pom.xml

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Build successful!${NC}"
else
    echo -e "${RED}✗ Build failed!${NC}"
    exit 1
fi

echo ""
echo -e "${YELLOW}Step 2: Starting Docker Compose...${NC}"

# Check if Docker is installed
if ! command -v docker &> /dev/null; then
    echo -e "${RED}Docker not found. Please install Docker.${NC}"
    exit 1
fi

docker-compose down 2>/dev/null || true
docker-compose up -d --build

if [ $? -eq 0 ]; then
    echo -e "${GREEN}✓ Docker Compose started!${NC}"
else
    echo -e "${RED}✗ Docker Compose failed!${NC}"
    exit 1
fi

echo ""
echo -e "${GREEN}================================================${NC}"
echo -e "${GREEN}✓ All services are running!${NC}"
echo -e "${GREEN}================================================${NC}"
echo ""
echo "Services:"
echo "  - Eureka Server: http://localhost:8761"
echo "  - API Gateway: http://localhost:8080"
echo "  - Auth Service: http://localhost:8081"
echo "  - Quiz Service: http://localhost:8082"
echo "  - WebSocket Service: http://localhost:8083"
echo "  - PostgreSQL: localhost:5433"
echo ""
echo -e "${YELLOW}Check logs with:${NC}"
echo "  docker-compose logs -f [service-name]"
echo ""
echo -e "${YELLOW}Stop services with:${NC}"
echo "  docker-compose down"
echo ""
