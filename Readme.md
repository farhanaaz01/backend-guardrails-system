# 🚀 Backend Engineering Assignment – Core API & Guardrails

This project is a Spring Boot microservice designed to simulate a social media backend with strict guardrails on bot activity.

The goal is to prevent uncontrolled bot interactions while maintaining performance and scalability using Redis.

---

## 🔧 Tech Stack

- Java 17+
- Spring Boot 3.x
- PostgreSQL
- Redis (Spring Data Redis)
- Docker

---

## 🧠 System Design Overview

Client → Controller → Service → Redis (Guardrails) → PostgreSQL (Storage)

- PostgreSQL acts as the source of truth
- Redis is used for real-time decision making

---

## ⚙️ Core APIs

- Create Post → `POST /api/posts`
- Add Comment → `POST /api/posts/{id}/comments`
- Like Post → `POST /api/posts/{id}/like`

---

## 🔥 Redis Guardrails

### 1. Virality Score
- Bot Reply → +1
- Human Like → +20
- Human Comment → +50

### 2. Horizontal Cap
- Maximum 100 bot replies per post
- Implemented using Redis atomic `INCR`

### 3. Vertical Cap
- Maximum comment depth = 20

### 4. Cooldown Cap
- A bot cannot interact with the same user more than once within 10 minutes
- Implemented using Redis keys with TTL

---

## 🔔 Notification Engine

To avoid notification spam:

- First interaction → Immediate notification
- Subsequent interactions → Stored in Redis list
- Scheduler batches and sends summarized notifications

---

## ⚡ Concurrency Handling

Redis atomic operations ensure thread-safe updates without using locks.  
This guarantees correct behavior even under high concurrent requests.

---

## 🛠️ Setup Instructions

1. Start services:
   docker-compose up -d

2. Run the Spring Boot application

3. Test APIs using Postman

---

## 🧪 Testing

- Postman used for API testing
- redis-cli used to verify:
   - Keys
   - TTL
   - Counters

---

## 📚 Key Learnings

- Distributed rate limiting using Redis
- Handling race conditions with atomic operations
- Designing stateless backend systems
- Event-driven scheduling with Spring

### Thread Safety Guarantee

Redis atomic operations such as INCR were used to enforce limits like bot reply caps. Since these operations are atomic, they prevent race conditions even under high concurrent requests without requiring locks.