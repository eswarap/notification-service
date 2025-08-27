# Notification Service

A simple Spring Boot application for managing notifications.

## Features

- Create notifications
- Retrieve all notifications
- Mark notifications as sent

## Technologies

- Java
- Spring Boot
- Spring Data JPA
- H2 Database (in-memory)
- Gradle

## Getting Started

### Prerequisites

- Java 17+
- Gradle

### Running the Application

1. Clone the repository:
    git clone https://github.com/eswarap/notification-service.git 
    cd notification-service
2. Build and run:
   ./gradlew bootRun
3. ### API Endpoints

- `POST /api/notifications`  
  Create a new notification.  
  **Body:** `{ "message": "Your message" }`

- `GET /api/notifications`  
  Retrieve all notifications.

- `POST /api/notifications/{id}/send`  
  Mark a notification as sent.

### Example Request

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/notifications" `
  -Method POST `
  -Headers @{ "Content-Type" = "application/json" } `
  -Body '{ "message": "Hello" }'
.