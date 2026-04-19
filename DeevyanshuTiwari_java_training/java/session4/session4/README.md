# 🚀 Spring Advance + Enterprise Flow — Session 4

> **Java Training | NucleusTeq**
> A full-featured Spring Boot TODO Application built with JPA, DTO pattern, Validation,
> SLF4J Logging, External Service Simulation, and Unit Testing (JUnit + Mockito).

---

## 📁 Project Structure

```
java/session4/
├── src/
│   ├── main/java/com/training/session4/
│   │   ├── Session4Application.java              ← Entry point
│   │   ├── controller/
│   │   │   └── TodoController.java               ← REST API layer (5 endpoints)
│   │   ├── service/
│   │   │   └── TodoService.java                  ← Business logic + logging
│   │   ├── repository/
│   │   │   └── TodoRepository.java               ← JpaRepository (auto CRUD)
│   │   ├── entity/
│   │   │   ├── Todo.java                         ← JPA Entity (maps to DB table)
│   │   │   └── TodoStatus.java                   ← Enum: PENDING / COMPLETED
│   │   ├── dto/
│   │   │   └── TodoDTO.java                      ← API layer model with validations
│   │   ├── client/
│   │   │   └── NotificationServiceClient.java    ← Simulated external service
│   │   └── exception/
│   │       ├── ErrorResponse.java                ← Standard JSON error format
│   │       ├── TodoNotFoundException.java         ← Custom 404 exception
│   │       ├── InvalidStatusException.java        ← Custom 400 exception
│   │       └── GlobalExceptionHandler.java        ← Catches all exceptions globally
│   ├── test/java/com/training/session4/
│   │   ├── service/
│   │   │   └── TodoServiceTest.java              ← 7 unit tests for service layer
│   │   └── controller/
│   │       └── TodoControllerTest.java           ← 11 unit tests for controller layer
│   └── resources/
│       └── application.properties                ← H2 DB + JPA config
└── pom.xml
```

---

## ⚙️ Tech Stack

| Technology        | Version / Detail              |
|-------------------|-------------------------------|
| Java              | 17                            |
| Spring Boot       | 3.x.x                         |
| Build Tool        | Maven                         |
| Database          | H2 (In-Memory)                |
| ORM               | Spring Data JPA (Hibernate)   |
| Logging           | SLF4J (built into Spring Boot)|
| Testing Framework | JUnit 5 + Mockito             |

---

## 🚀 How to Run

**Step 1 — Clone the repository**
```bash
git clone https://github.com/YourName/YourName_java_training.git
cd YourName_java_training/java/session4
```

**Step 2 — Run the application**
```bash
./mvnw spring-boot:run
```

**Step 3 — App starts at**
```
http://localhost:8080
```

**Step 4 — View H2 Database in browser (optional)**
```
URL      : http://localhost:8080/h2-console
JDBC URL : jdbc:h2:mem:tododb
Username : sa
Password : (leave empty)
```

---

## 📡 API Reference

### **1. Create TODO**

| Method | Endpoint | Success Code |
|--------|----------|--------------|
| POST   | `/todos` | 201 Created  |

**Request Body:**
```json
{
  "title":       "Buy Groceries",
  "description": "Milk, eggs, bread",
  "status":      "PENDING"
}
```

**Notes:**
- `title` is required, minimum 3 characters
- `status` is optional — defaults to `PENDING` if not provided
- `createdAt` is set automatically by the server — user cannot control it
- Triggers `NotificationServiceClient` automatically after creation

**Success Response (201):**
```json
{
  "id":          1,
  "title":       "Buy Groceries",
  "description": "Milk, eggs, bread",
  "status":      "PENDING"
}
```

---

### **2. Get All TODOs**

| Method | Endpoint | Success Code |
|--------|----------|--------------|
| GET    | `/todos` | 200 OK       |

**Response:**
```json
[
  { "id": 1, "title": "Buy Groceries", "description": "Milk, eggs, bread", "status": "PENDING"   },
  { "id": 2, "title": "Read Book",     "description": "Java Spring Boot",   "status": "COMPLETED" }
]
```

---

### **3. Get TODO by ID**

| Method | Endpoint      | Success Code |
|--------|---------------|--------------|
| GET    | `/todos/{id}` | 200 OK       |

**Success Response:**
```json
{ "id": 1, "title": "Buy Groceries", "description": "Milk, eggs, bread", "status": "PENDING" }
```

**Not Found Response (404):**
```json
{
  "status":    404,
  "message":   "Todo not found with id: 99",
  "timestamp": "2024-01-10T10:30:00"
}
```

---

### **4. Update TODO**

| Method | Endpoint      | Success Code |
|--------|---------------|--------------|
| PUT    | `/todos/{id}` | 200 OK       |

**Request Body:**
```json
{
  "title":       "Buy Groceries Updated",
  "description": "Also need butter",
  "status":      "COMPLETED"
}
```

**Allowed Status Transitions:**

| From        | To          | Result  |
|-------------|-------------|---------|
| `PENDING`   | `COMPLETED` | ✅ Allowed |
| `COMPLETED` | `PENDING`   | ✅ Allowed |
| `PENDING`   | `PENDING`   | ❌ Rejected |
| `COMPLETED` | `COMPLETED` | ❌ Rejected |

---

### **5. Delete TODO**

| Method | Endpoint      | Success Code |
|--------|---------------|--------------|
| DELETE | `/todos/{id}` | 200 OK       |

**Success Response:**
```
"Todo with id 1 deleted successfully"
```

**Not Found Response (404):**
```json
{
  "status":    404,
  "message":   "Todo not found with id: 99",
  "timestamp": "2024-01-10T10:30:00"
}
```

---

## ❌ Error Responses

All errors follow this standard JSON format:
```json
{
  "status":    400,
  "message":   "title: Title cannot be blank",
  "timestamp": "2024-01-10T10:30:00"
}
```

| Scenario                    | Status | Message                                          |
|-----------------------------|--------|--------------------------------------------------|
| Todo not found              | 404    | Todo not found with id: {id}                     |
| Blank title                 | 400    | title: Title cannot be blank                     |
| Title less than 3 chars     | 400    | title: Title must be at least 3 characters       |
| Invalid status transition   | 400    | Invalid status transition from X to Y            |
| Any unexpected error        | 500    | Something went wrong: ...                        |

---

## 🧠 Key Concepts Demonstrated

### 1. JPA Entity + H2 Database
The `Todo` class maps directly to a `todos` table in H2. No SQL needed — JPA handles it.
```java
@Entity
@Table(name = "todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TodoStatus status;  // stored as "PENDING" / "COMPLETED"
}
```

### 2. DTO Pattern (No Direct Entity Exposure)
The API never returns the `Todo` entity directly. It always converts to `TodoDTO` first.
```
API Request  → TodoDTO → (manual convert) → Todo Entity → Database
Database     → Todo Entity → (manual convert) → TodoDTO → API Response
```

### 3. `@Valid` Validation
Validation rules are declared on `TodoDTO` fields — Spring checks them automatically.
```java
@NotBlank(message = "Title cannot be blank")
@Size(min = 3, message = "Title must be at least 3 characters")
private String title;
```

### 4. Constructor Injection
Dependencies are provided through constructors — no `@Autowired` on fields.
```java
public TodoService(TodoRepository todoRepository,
                   NotificationServiceClient notificationServiceClient) {
    this.todoRepository            = todoRepository;
    this.notificationServiceClient = notificationServiceClient;
}
```

### 5. JpaRepository (Free CRUD Methods)
```java
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // save(), findAll(), findById(), deleteById(), existsById() — all free!
}
```

### 6. Global Exception Handling
`GlobalExceptionHandler` with `@RestControllerAdvice` catches all exceptions in one place.
Controllers have zero try-catch blocks.

---

## 🔍 Logging (SLF4J)

SLF4J Logger is added to **Controller**, **Service**, and **NotificationServiceClient**.

```java
private static final Logger log = LoggerFactory.getLogger(TodoService.class);

log.info("Creating new todo with title: {}", dto.getTitle());
log.warn("Todo not found with id: {}", id);
```

| Log Level    | Where used                        | Purpose                         |
|--------------|-----------------------------------|---------------------------------|
| `log.info()` | Controller + Service              | Normal operations               |
| `log.warn()` | Service (findById failures)       | Resource not found warnings     |
| `log.info()` | NotificationServiceClient         | Notification dispatch logs      |

**Sample console output when creating a TODO:**
```
INFO  TodoController            : Request received to create todo with title: Buy Groceries
INFO  TodoService               : Creating new todo with title: Buy Groceries
INFO  TodoService               : Todo saved to database with id: 1
INFO  NotificationServiceClient : Notification sent: New TODO created: 'Buy Groceries' with id: 1
INFO  TodoController            : Todo created successfully with id: 1
```

---

## 🔔 NotificationServiceClient

Located in `client/NotificationServiceClient.java`. It is a `@Component` that simulates calling an external notification system (e.g. email/SMS service).

It is called automatically inside `TodoService.createTodo()` after every successful TODO creation:
```java
notificationServiceClient.sendNotification(
    "New TODO created: '" + saved.getTitle() + "' with id: " + saved.getId()
);
```

In a real project this would use `RestTemplate` or `WebClient` to call an external REST API.

---

## 🧪 Unit Testing

| Item           | Detail            |
|----------------|-------------------|
| Framework      | JUnit 5 + Mockito |
| Coverage       | 85%+              |
| Service Tests  | 11 test cases     |
| Controller Tests | 7 test cases      |

### Run all tests
```bash
./mvnw test
```

### Run with coverage report (IntelliJ)
Right-click `test` folder → **Run All Tests with Coverage**

---

### TodoServiceTest — Test Cases

| Test Method | What it verifies |
|---|---|
| `createTodo_ShouldReturnCreatedTodo` | Todo is saved and returned correctly |
| `createTodo_ShouldDefaultStatusToPending` | Status defaults to PENDING when null |
| `getAllTodos_ShouldReturnListOfTodos` | Returns correct number of todos |
| `getAllTodos_ShouldReturnEmptyList` | Works correctly when no todos exist |
| `getTodoById_ShouldReturnTodo_WhenExists` | Returns correct todo by id |
| `getTodoById_ShouldThrowException_WhenNotFound` | Throws TodoNotFoundException for missing id |
| `updateTodo_ShouldUpdateSuccessfully` | Updates fields and status correctly |
| `updateTodo_ShouldThrowException_WhenNotFound` | Throws exception for missing id |
| `updateTodo_ShouldThrowException_InvalidTransition` | Rejects invalid status transition |
| `deleteTodo_ShouldDeleteSuccessfully` | Deletes and returns success message |
| `deleteTodo_ShouldThrowException_WhenNotFound` | Throws exception, never calls deleteById |

---

### TodoControllerTest — Test Cases

| Test Method | What it verifies |
|---|---|
| `createTodo_ShouldReturn201_WhenValidInput` | Returns 201 with correct JSON |
| `getAllTodos_ShouldReturn200_WithListOfTodos` | Returns 200 with correct list size |
| `getTodoById_ShouldReturn200_WhenFound` | Returns correct todo JSON |
| `getTodoById_ShouldReturn404_WhenNotFound` | Returns 404 for missing todo |
| `updateTodo_ShouldReturn200_WhenValid` | Returns updated todo with 200 |
| `deleteTodo_ShouldReturn200_WhenDeleted` | Returns success message with 200 |
| `deleteTodo_ShouldReturn404_WhenNotFound` | Returns 404 for missing todo |

---

### How Mocking Works
```
Real app  :  Service → Repository → H2 Database
Test      :  Service → Mock Repository (fake, no DB needed)

Control what mock returns:
when(todoRepository.findById(1L)).thenReturn(Optional.of(sampleTodo));

Verify a method was actually called:
verify(notificationServiceClient, times(1)).sendNotification(anyString());

Verify a method was NEVER called:
verify(todoRepository, never()).deleteById(any());
```

---

## 🗂️ Layered Architecture

```
HTTP Request
      ↓
Controller    → Receives request, validates with @Valid, returns response
      ↓
Service       → Business logic, DTO ↔ Entity conversion, status validation, logging
      ↓
Repository    → JpaRepository handles all DB operations
      ↓
H2 Database   → In-memory database (resets on every app restart)

                            ↕ (on create)
               NotificationServiceClient
               (simulates external API call)
```

---

## 🧪 Testing the APIs (Postman)

**Create valid todo:**
```
POST http://localhost:8080/todos
Content-Type: application/json

{ "title": "Buy Groceries", "description": "Milk and eggs" }
```

**Create with blank title → 400:**
```
POST http://localhost:8080/todos
Content-Type: application/json

{ "title": "", "description": "Some task" }
```

**Create with short title → 400:**
```
POST http://localhost:8080/todos
Content-Type: application/json

{ "title": "Hi" }
```

**Get all todos:**
```
GET http://localhost:8080/todos
```

**Get by id:**
```
GET http://localhost:8080/todos/1
```

**Get non-existing → 404:**
```
GET http://localhost:8080/todos/99
```

**Update status PENDING → COMPLETED:**
```
PUT http://localhost:8080/todos/1
Content-Type: application/json

{ "title": "Buy Groceries", "description": "Done", "status": "COMPLETED" }
```

**Delete todo:**
```
DELETE http://localhost:8080/todos/1
```

---

## 👨‍💻 Author

**Deevyanshu Tiwari**
