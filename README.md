# 🎮 Quiz Battle Royale

Multiplayer quiz game with real-time battles - Full-stack monorepo

## 📦 Project Structure

```
quizBattleRoyal/
├── frontend/                # Next.js 14 + TypeScript + Tailwind + shadcn/ui
│   ├── src/
│   │   ├── app/            # Next.js App Router
│   │   ├── components/     # React components
│   │   ├── contexts/       # React contexts (i18n)
│   │   ├── locales/        # Translations (FR/EN)
│   │   └── lib/            # Utilities
│   └── package.json
│
├── backend/                 # Spring Boot Microservices
│   ├── eureka-server/      # Service Discovery (port 8761)
│   ├── api-gateway/        # API Gateway (port 8080)
│   ├── auth-service/       # Authentication (port 8081)
│   ├── quiz-service/       # Quiz management (port 8082)
│   ├── websocket-service/  # Real-time WebSocket (port 8083)
│   ├── docker-compose.yml  # Full stack orchestration
│   └── docker-compose.dev.yml  # PostgreSQL only
│
├── rebuild-service.ps1     # Rebuild specific service
├── watch-and-rebuild.ps1   # Auto-rebuild on code change
└── .gitignore              # Global ignore rules
```

## 🚀 Quick Start

### Prerequisites

- Node.js 18+
- Java 17+
- Maven 3.6+
- Docker Desktop
- PostgreSQL (optional, Docker provides one)

### 1️⃣ Start Database

```powershell
docker compose -f backend/docker-compose.dev.yml up -d
```

PostgreSQL runs on **port 5433** (not 5432 to avoid conflicts)

### 2️⃣ Start Frontend

```powershell
cd frontend
npm install
npm run dev
```

Frontend: http://localhost:3000

### 3️⃣ Start Backend Services

```powershell
cd backend
./build-all.ps1                    # Build all JARs
docker compose up -d --build       # Start all services
```

## 🛠️ Development Workflows

### Auto-Rebuild on Save

Watch a service and automatically rebuild when files change:

```powershell
./watch-and-rebuild.ps1 auth-service
```

### Manual Rebuild

Rebuild a specific service:

```powershell
./rebuild-service.ps1 quiz-service
```

### View Logs

```powershell
docker logs quiz-auth-service -f
docker logs quiz-quiz-service -f
docker logs quiz-websocket-service -f
```

## 🌐 Services & Ports

| Service      | Port | Description             | URL                   |
| ------------ | ---- | ----------------------- | --------------------- |
| Frontend     | 3000 | Next.js app             | http://localhost:3000 |
| API Gateway  | 8080 | Single entry point      | http://localhost:8080 |
| Auth Service | 8081 | Authentication          | http://localhost:8081 |
| Quiz Service | 8082 | Quiz management         | http://localhost:8082 |
| WebSocket    | 8083 | Real-time communication | ws://localhost:8083   |
| Eureka       | 8761 | Service discovery       | http://localhost:8761 |
| PostgreSQL   | 5433 | Database                | localhost:5433        |

## 🗄️ Database

**Connection:**

- Host: `localhost:5433`
- Database: `quizbattle`
- User: `postgres`
- Password: `postgres`

## 🧪 Tech Stack

### Frontend

- **Framework:** Next.js 14 (App Router)
- **Language:** TypeScript
- **Styling:** Tailwind CSS
- **Components:** shadcn/ui
- **i18n:** react-i18next (FR/EN)

### Backend

- **Framework:** Spring Boot 3.3.0
- **Discovery:** Netflix Eureka
- **Gateway:** Spring Cloud Gateway
- **Database:** PostgreSQL 15
- **Build:** Maven
- **Deployment:** Docker Compose

## 📝 Git Workflow

### Initial Setup (Already Done ✅)

```powershell
git init
git add .
git commit -m "🎉 Initial commit"
```

### Connect to GitHub

```powershell
git remote add origin https://github.com/YOUR_USERNAME/quiz-battle-royale.git
git branch -M main
git push -u origin main
```

### Regular Commits

```powershell
git add .
git commit -m "✨ Add new feature"
git push
```

## 🔧 Scripts Reference

### Backend Scripts

- `backend/build-all.ps1` - Build all services with Maven
- `backend/docker-compose.yml` - Full stack (DB + all services)
- `backend/docker-compose.dev.yml` - PostgreSQL only

### Root Scripts

- `rebuild-service.ps1 <service>` - Rebuild one service
- `watch-and-rebuild.ps1 <service>` - Auto-rebuild on change

### Frontend Scripts

- `npm run dev` - Development server
- `npm run build` - Production build
- `npm run start` - Production server

## 📂 .gitignore

The monorepo ignores:

- ✅ `.env*` files (all environments)
- ✅ `node_modules/`
- ✅ `target/` (Maven builds)
- ✅ `.next/` (Next.js build)
- ✅ IDE files (`.vscode/`, `.idea/`)
- ✅ Docker overrides
- ✅ Logs and temp files

## 🎯 Next Steps

Since the backend is empty shells, implement:

1. **Auth Service:** User registration, login, JWT tokens
2. **Quiz Service:** Quiz CRUD, questions, leaderboards
3. **WebSocket Service:** Real-time game lobbies, battle logic
4. **Frontend:** Connect to APIs, game UI

## 📚 Documentation

- [Backend README](backend/README.md) - Microservices architecture details
- [Frontend README](frontend/README.md) - Next.js setup and structure

---

**Ready to code!** 🚀
