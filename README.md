# 🎮 Quiz Battle Royale

Une application web **quiz multijoueur en temps réel**, façon Battle Royale, où les joueurs s'affrontent pour être le dernier en vie. Chaque question est chronométrée, et répondre trop lentement ou faux entraîne l'élimination.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3.0-brightgreen)
![Next.js](https://img.shields.io/badge/Next.js-14-black)
![TypeScript](https://img.shields.io/badge/TypeScript-5-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)

---

## 📝 Contexte

Le projet a été conçu pour :

- **Apprendre et démontrer** l'utilisation des WebSockets avec Spring Boot et Next.js
- Gérer **des sessions multi-joueurs synchronisées en temps réel**
- Créer une **expérience interactive et ludique**
- Montrer des compétences avancées en **architecture microservices** et **frontend dynamique**

Le concept s'inspire des jeux de quiz type Kahoot, mais avec une dimension **compétitive style Battle Royale**, élimination en direct et power-ups stratégiques.

---

## 🎯 Fonctionnalités principales

### ✅ Authentification

- Création de compte / login avec JWT
- Gestion des profils utilisateurs
- Statistiques personnelles (parties jouées, victoires, score total)

### 🎲 Lobby / Rooms

- Création d'une session de quiz avec code unique
- Rejoindre une session existante
- Voir les joueurs connectés en temps réel
- Le host peut démarrer la partie

### 🎮 Gameplay Quiz

- Questions chronométrées (5-10s par question)
- Élimination instantanée si mauvaise réponse ou timeout
- Timer synchronisé côté serveur (anti-triche)
- Push des questions à tous les joueurs via WebSocket
- Power-ups : "50/50", "+5s", "joker" (à venir)

### 🏆 Leaderboard et notifications

- Classement live mis à jour en temps réel
- Notifications pour chaque élimination
- Animations pour les gagnants / éliminés
- Historique des parties

---

## 🏗 Architecture

### Backend (Spring Boot Microservices)

```
┌─────────────────┐
│   API Gateway   │ :8080
│  (Spring Cloud) │
└────────┬────────┘
         │
    ┌────┴────┬──────────┬──────────┐
    │         │          │          │
┌───▼──┐  ┌──▼───┐  ┌───▼───┐  ┌──▼────┐
│ Auth │  │ Quiz │  │WebSocket│ │Eureka │
│:8081 │  │:8082 │  │ :8083  │ │ :8761 │
└──────┘  └──────┘  └────────┘ └───────┘
    │         │          │
┌───▼───┐ ┌──▼───┐  ┌──▼───┐
│Postgres│ │Postgres│ │Redis │
│ Auth  │ │ Quiz  │ │      │
└───────┘ └───────┘ └──────┘
```

**Microservices :**

- **Eureka Server** (8761) - Service Discovery
- **API Gateway** (8080) - Point d'entrée unique, routage, authentification JWT
- **Auth Service** (8081) - Authentification, gestion utilisateurs (PostgreSQL)
- **Quiz Service** (8082) - Questions, catégories, statistiques (PostgreSQL)
- **WebSocket Service** (8083) - Communication temps réel, rooms, gameplay (Redis)

### Frontend (Next.js)

```
frontend/
├── src/
│   ├── app/              # Pages Next.js (App Router)
│   ├── components/       # Composants React + shadcn/ui
│   ├── contexts/         # Context providers (i18n, Auth, WebSocket)
│   ├── lib/              # Utilitaires, i18n config
│   └── locales/          # Traductions (FR/EN)
```

**Technologies :**

- Next.js 14 avec App Router
- TypeScript
- Tailwind CSS
- shadcn/ui pour les composants
- react-i18next pour l'internationalisation
- STOMP over WebSocket pour le temps réel

---

## 🚀 Installation

### Prérequis

- **Java 17+**
- **Maven 3.6+**
- **Node.js 18+**
- **Docker & Docker Compose**
- **PostgreSQL 15+** (ou via Docker)
- **Redis 7+** (ou via Docker)

### Installation complète avec Docker

```bash
# 1. Cloner le repo
git clone <repo-url>
cd quizBattleRoyal

# 2. Lancer le backend (microservices + DB)
cd backend
docker-compose up -d

# 3. Lancer le frontend
cd ../frontend
npm install
npm run dev
```

**URLs après installation :**

- Frontend : http://localhost:3000
- API Gateway : http://localhost:8080
- Eureka Dashboard : http://localhost:8761

### Installation manuelle (développement)

**Backend :**

```bash
cd backend

# Démarrer les bases de données
docker run -d -p 5432:5432 -e POSTGRES_DB=quiz_auth -e POSTGRES_PASSWORD=postgres postgres:15-alpine
docker run -d -p 5433:5432 -e POSTGRES_DB=quiz_data -e POSTGRES_PASSWORD=postgres postgres:15-alpine
docker run -d -p 6379:6379 redis:7-alpine

# Compiler chaque service (dans l'ordre)
cd eureka-server && mvn clean package && cd ..
cd api-gateway && mvn clean package && cd ..
cd auth-service && mvn clean package && cd ..
cd quiz-service && mvn clean package && cd ..
cd websocket-service && mvn clean package && cd ..

# Démarrer les services (terminaux séparés)
cd eureka-server && mvn spring-boot:run
cd api-gateway && mvn spring-boot:run
cd auth-service && mvn spring-boot:run
cd quiz-service && mvn spring-boot:run
cd websocket-service && mvn spring-boot:run
```

**Frontend :**

```bash
cd frontend
npm install
npm run dev
```

---

## 📡 API Endpoints

### Auth Service

```bash
# Inscription
POST /api/auth/register
{
  "username": "player1",
  "email": "player1@example.com",
  "password": "password123",
  "displayName": "Player One"
}

# Connexion
POST /api/auth/login
{
  "username": "player1",
  "password": "password123"
}
```

### Quiz Service

```bash
# Questions aléatoires
GET /api/quiz/questions/random?count=10

# Question spécifique
GET /api/quiz/questions/{id}

# Valider une réponse
POST /api/quiz/questions/{questionId}/validate
{
  "answerId": 123
}
```

### WebSocket Service

**Connexion :** `ws://localhost:8080/api/ws/ws`

```javascript
// Rejoindre une room
stompClient.send(
  "/app/room.join",
  {},
  JSON.stringify({
    roomCode: "ABC123",
    userId: 1,
    username: "player1",
    displayName: "Player One",
  }),
);

// Soumettre une réponse
stompClient.send(
  "/app/game.answer",
  {},
  JSON.stringify({
    roomCode: "ABC123",
    userId: 1,
    answerId: "answer-1",
    timestamp: Date.now(),
  }),
);

// Démarrer la partie (host only)
stompClient.send("/app/game.start", {}, "ABC123");
```

**Topics de souscription :**

- `/topic/room.{roomCode}` - Événements de la room
- `/topic/leaderboard.{roomCode}` - Mises à jour du classement

---

## 🛠 Stack Technique

### Backend

- **Spring Boot 3.3.0**
- Spring Cloud Gateway
- Spring Cloud Netflix Eureka
- Spring WebSocket + STOMP
- Spring Security + JWT
- Spring Data JPA
- PostgreSQL 15
- Redis 7
- Lombok

### Frontend

- **Next.js 14** (App Router)
- **TypeScript**
- **Tailwind CSS**
- **shadcn/ui**
- react-i18next (i18n FR/EN)
- STOMP.js (WebSocket)

---

## 📊 Base de données

### Auth Service (PostgreSQL)

```sql
users
- id, username, email, password
- display_name, avatar_url
- total_games, total_wins, total_score
- is_active, created_at, updated_at
```

### Quiz Service (PostgreSQL)

```sql
categories
- id, name, description, icon_url, is_active

questions
- id, question_text, difficulty_level
- time_limit, points, category_id
- is_active, created_at, updated_at

answers
- id, answer_text, is_correct, question_id

game_sessions
- id, room_code, host_user_id, status
- winner_user_id, total_players, total_questions
- started_at, finished_at

player_stats
- id, user_id, session_id
- final_score, correct_answers, wrong_answers
- rank_position, is_winner
```

---

## 🔐 Sécurité

- **JWT** pour l'authentification
- Tokens validés par l'API Gateway
- WebSockets sécurisés avec JWT dans le header `Authorization`
- Passwords hashés avec BCrypt
- CORS configuré pour le frontend (localhost:3000)

---

## 🌐 Internationalisation

Le frontend supporte **Français** et **Anglais** via react-i18next avec Context Provider.

```typescript
import { useTranslation } from 'react-i18next';

const { t } = useTranslation();
<h1>{t('common.welcome')}</h1>
```

Fichiers de traduction : `frontend/src/locales/fr.json` et `en.json`

---

## 🎨 Interface

Composants UI avec **shadcn/ui** :

- Buttons, Cards, Dialogs
- Form inputs avec validation
- Toasts pour notifications
- Animations CSS pour timer et éliminations

---

## 📅 Roadmap

- [x] Architecture microservices
- [x] Auth avec JWT
- [x] WebSocket + STOMP
- [x] Rooms et lobby
- [x] Questions et réponses
- [x] Leaderboard en temps réel
- [ ] Power-ups (50/50, +5s, joker)
- [ ] Mode spectateur
- [ ] Chat limité / emojis
- [ ] Thèmes de quiz personnalisés
- [ ] Tournois multi-sessions
- [ ] Export résultats PDF

---

## 🤝 Contribution

Les contributions sont les bienvenues ! Ouvrez une issue ou une pull request.

---

## 📄 License

MIT

---

## 👨‍💻 Auteur

Projet réalisé pour démontrer des compétences en :

- Architecture microservices
- WebSocket temps réel
- Spring Boot + Next.js
- Sécurité JWT
- UI/UX interactive

---

**Bon jeu ! 🎮🏆**
