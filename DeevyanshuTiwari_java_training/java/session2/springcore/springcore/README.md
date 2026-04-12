# 🌱 Spring Core Assignment — Session 2

> **Java Training | NucleusTeq**
> A Spring Boot REST API project demonstrating Core Spring concepts — IoC, Dependency Injection, Layered Architecture, and Exception Handling.

---

## 📁 Project Structure

```
java/session2/
├── src/main/java/com/training/springcore/
│   ├── SpringcoreApplication.java        ← Entry point
│   ├── controller/
│   │   ├── UserController.java           ← User REST APIs
│   │   ├── NotificationController.java   ← Notification API
│   │   └── MessageController.java        ← Message Formatter API
│   ├── service/
│   │   ├── UserService.java              ← User business logic
│   │   ├── NotificationService.java      ← Notification logic
│   │   └── MessageService.java           ← Message decision logic
│   ├── repository/
│   │   └── UserRepository.java           ← In-memory data store
│   ├── model/
│   │   └── User.java                     ← User data model
│   ├── component/
│   │   ├── NotificationComponent.java    ← Notification helper
│   │   ├── ShortMessageFormatter.java    ← Short message format
│   │   └── LongMessageFormatter.java     ← Long message format
│   └── exception/
│       ├── ErrorResponse.java            ← Standard error JSON format
│       ├── UserNotFoundException.java    ← Custom 404 exception
│       ├── InvalidInputException.java    ← Custom 400 exception
│       └── GlobalExceptionHandler.java   ← Catches all exceptions
└── src/main/resources/
    └── application.properties
```

---

## ⚙️ Tech Stack

| Technology    | Version  |
|---------------|----------|
| Java          | 17       |
| Spring Boot   | 3.x.x    |
| Build Tool    | Maven    |
| Data Storage  | In-Memory (ArrayList) |

---

## 🚀 How to Run

**Step 1 — Clone the repository**
```bash
git clone https://github.com/YourName/YourName_java_training.git
cd YourName_java_training/java/session2
```

**Step 2 — Run the application**
```bash
./mvnw spring-boot:run
```

**Step 3 — App starts at**
```
http://localhost:8080
```

---

## 📡 API Reference

### 👤 User Management APIs

| Method | Endpoint        | Description          |
|--------|-----------------|----------------------|
| GET    | `/users`        | Get all users        |
| GET    | `/users/{id}`   | Get user by ID       |
| POST   | `/users`        | Create a new user    |

**GET /users** — Returns all users
```json
[
  { "id": 1, "name": "Rahul Sharma", "email": "rahul@gmail.com" },
  { "id": 2, "name": "Priya Singh",  "email": "priya@gmail.com" }
]
```

**GET /users/1** — Returns single user
```json
{ "id": 1, "name": "Rahul Sharma", "email": "rahul@gmail.com" }
```

**POST /users** — Create user (send this JSON in body)
```json
{ "name": "Neha Jain", "email": "neha@gmail.com" }
```

---

### 🔔 Notification API

| Method | Endpoint                              | Description           |
|--------|---------------------------------------|-----------------------|
| GET    | `/notification/send?recipient=Rahul`  | Send a notification   |

**Example Response:**
```
Hello Rahul! Your notification has been sent successfully.
```

---

### 💬 Dynamic Message Formatter API

| Method | Endpoint                                    | Description              |
|--------|---------------------------------------------|--------------------------|
| GET    | `/message?type=SHORT&topic=Promotion`       | Get short message        |
| GET    | `/message?type=LONG&topic=Promotion`        | Get long message         |

**SHORT Response:**
```
Quick update: Promotion
```

**LONG Response:**
```
Dear User, we wanted to provide you with a detailed update regarding 
the following topic: Promotion. Please review at your earliest convenience. Thank you.
```

---

## ❌ Error Responses

All errors return a standard JSON format:
```json
{
  "status": 404,
  "message": "User not found with id: 99",
  "timestamp": "2024-01-10T10:30:00"
}
```

| Scenario                  | Status | Message                          |
|---------------------------|--------|----------------------------------|
| User ID not found         | 404    | User not found with id: {id}     |
| Negative or zero ID       | 400    | ID must be a positive number     |
| Empty name on create      | 400    | Name cannot be empty             |
| Invalid email format      | 400    | Email must contain @             |
| Empty notification target | 400    | Recipient name cannot be empty   |
| Wrong message type        | 400    | Type must be SHORT or LONG       |
| Any unexpected error      | 500    | Something went wrong: ...        |

---

## 🧠 Spring Concepts Demonstrated

### 1. IoC — Inversion of Control
Spring manages the creation of all objects. You never write `new UserService()` manually — Spring creates and provides it automatically.

### 2. Dependency Injection (Constructor-based)
Every class receives its dependencies through the constructor:
```java
@Service
public class UserService {
    private final UserRepository userRepository;

    // Spring injects UserRepository here automatically
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```

### 3. Layered Architecture
```
HTTP Request
     ↓
Controller   → Receives request, returns response (no business logic)
     ↓
Service      → All business logic and validation lives here
     ↓
Repository   → Handles all data operations
```

### 4. Component Scanning
Spring automatically detects classes annotated with:

| Annotation          | Used For                        |
|---------------------|---------------------------------|
| `@RestController`   | HTTP request handler            |
| `@Service`          | Business logic class            |
| `@Repository`       | Data access class               |
| `@Component`        | General-purpose helper class    |

### 5. Global Exception Handling
`GlobalExceptionHandler` with `@RestControllerAdvice` catches all exceptions across the entire project in one place — controllers stay clean with no try-catch blocks.

---

## 🗂️ Key Design Decisions

- **Constructor injection only** — no `@Autowired` on fields, as required
- **No real database** — `ArrayList` used as in-memory store inside `UserRepository`
- **Decision logic in Service** — `MessageController` passes `type` to `MessageService`, which decides whether to use `ShortMessageFormatter` or `LongMessageFormatter`. No if-else in controller.
- **Separation of concerns** — business logic, data access, and request handling are in completely separate layers

---

## 🧪 Testing the APIs (using Postman or Browser)

**Test GET all users:**
```
GET http://localhost:8080/users
```

**Test GET user not found (triggers 404):**
```
GET http://localhost:8080/users/99
```

**Test POST create user:**
```
POST http://localhost:8080/users
Content-Type: application/json

{ "name": "Deepak Pandey", "email": "deepak@gmail.com" }
```

**Test invalid email (triggers 400):**
```
POST http://localhost:8080/users
Content-Type: application/json

{ "name": "Test User", "email": "invalidemail" }
```

**Test notification:**
```
GET http://localhost:8080/notification/send?recipient=Rahul
```

**Test short message:**
```
GET http://localhost:8080/message?type=SHORT&topic=NewSale
```

**Test wrong message type (triggers 400):**
```
GET http://localhost:8080/message?type=MEDIUM&topic=NewSale
```

---

## 👨‍💻 Author

**Deevyanshu Tiwari**
