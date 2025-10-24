# BFit Project

BFit is a personal project aimed at learning microservices architecture, strengthening backend development skills, and integrating AI-driven insights in a fitness tracking application. The system demonstrates secure authentication, event-driven communication, and real-time recommendation generation.

---

## Overview

BFit is built using a microservices architecture where each service handles a specific responsibility.  
Users can log activities, which are processed by an AI engine to provide real-time recommendations and insights.  
The system is designed for scalability, real-time processing, and secure user authentication.

---

## Microservices

### User Service
- Built with Spring Boot + PostgreSQL
- Manages user registration, authentication, and profile information
- Syncs user data with Keycloak for centralized authentication and OAuth2-based security

### Activity Service
- Built with Spring Boot + MongoDB
- Records user activities and fitness metrics
- Publishes activity events to Kafka for downstream processing by the AI Service

### AI Service
- Listens to Kafka events triggered by user activity logs
- Integrates with Google Gemini API to analyze activity data and generate personalized recommendations

### Gateway Service
- Serves as the single entry point for requests from the frontend or Postman
- Handles routing to various microservices
- Implements JWT validation and user synchronization with the User Service via WebFlux Filter

---

## Supporting Services
- Keycloak: OAuth2 authentication and authorization
- Eureka Server: Dynamic service discovery for microservices
- Config Server: Centralized configuration management

---

## Tech Stack

| Layer | Technologies |
|-------|---------------|
| Frontend | React.js, Redux Toolkit |
| Backend | Spring Boot (WebFlux, Data JPA, Security, Kafka) |
| Authentication | Keycloak (OAuth2) |
| Databases | PostgreSQL, MongoDB |
| Messaging | Apache Kafka |
| AI Integration | Google Gemini API |
| Deployment and Containerization | Docker, Docker Compose |
| Service Discovery | Eureka |
| Configuration Management | Spring Cloud Config |

---

## How It Works

1. The user interacts with the system via the frontend or Postman.  
2. Requests go through the Gateway Service, which validates tokens and routes them.  
3. Keycloak manages OAuth2 authentication and user authorization.  
4. Upon successful login:
   - User data is automatically synchronized with the User Service.
   - Logged activities are sent to the Activity Service.
   - Activity data is saved in MongoDB and an event is published to Kafka.
5. The AI Service consumes Kafka events, calls the Google Gemini API to analyze data, and generates personalized recommendations.
6. Recommendations are sent back through the Gateway and displayed on the frontend dashboard.

---

## Communication Flow

User logs in  
↓  
Gateway verifies Keycloak token  
↓  
User auto-synced in User Service  
↓  
User logs activity  
↓  
Activity Service saves data and publishes event to Kafka  
↓  
Kafka triggers AI Service  
↓  
Gemini API generates insights  
↓  
Recommendations sent back to frontend dashboard

---

## Key Learnings

This project strengthened knowledge in:
- Designing microservices and event-driven architectures
- Implementing secure authentication and authorization
- Building real-time communication flows
- Integrating AI-based recommendation systems

---


