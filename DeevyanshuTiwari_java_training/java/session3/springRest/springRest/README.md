# 🌐 Spring and REST Assignment — Session 3

> **Java Training | NucleusTeq**
> A Spring Boot REST API project demonstrating REST concepts — Request Parameters, Request Body, Path Variables, Filtering, Validation, and Delete with Confirmation.

---

## 📁 Project Structure

```
java/session3/
├── src/main/java/com/training/springRest/
│   ├── SpringRestApplication.java          ← Entry point
│   ├── controller/
│   │   ├── UserController.java             ← Search & Delete APIs
│   │   └── SubmitController.java           ← Submit data API
│   ├── service/
│   │   ├── UserService.java                ← Filter & delete logic
│   │   └── SubmitService.java              ← Submission validation logic
│   ├── repository/
│   │   └── UserRepository.java             ← In-memory user data store
│   ├── model/
│   │   ├── User.java                       ← User data model
│   │   └── SubmitRequest.java              ← Submit request model
│   └── exception/
│       ├── ErrorResponse.java              ← Standard error JSON format
│       ├── UserNotFoundException.java      ← Custom 404 exception
│       ├── InvalidInputException.java      ← Custom 400 exception
│       └── GlobalExceptionHandler.java     ← Catches all exceptions
└── src/main/resources/
    └── application.properties
```

---

## ⚙️ Tech Stack

| Technology   | Version              |
|--------------|----------------------|
| Java         | 17                   |
| Spring Boot  | 3.x.x                |
| Build Tool   | Maven                |
| Data Storage | In-Memory (ArrayList)|

---

## 🚀 How to Run

**Step 1 — Clone the repository**
```bash
git clone https://github.com/YourName/YourName_java_training.git
cd YourName_java_training/java/session3
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

### **Task 1 — User Search API**

| Method | Endpoint          | Description                        |
|--------|-------------------|------------------------------------|
| GET    | `/users/search`   | Search/filter users by parameters  |

#### Request Parameters (all optional)

| Parameter | Type    | Description                          |
|-----------|---------|--------------------------------------|
| `name`    | String  | Filter by name (case-insensitive)    |
| `age`     | Integer | Filter by age (exact match)          |
| `role`    | String  | Filter by role (case-insensitive)    |

#### Behavior

| URL | Result |
|-----|--------|
| `/users/search` | Returns ALL 7 users |
| `/users/search?name=priya` | Returns users named Priya |
| `/users/search?age=30` | Returns users aged 30 |
| `/users/search?role=admin` | Returns users with ADMIN role |
| `/users/search?age=30&role=USER` | Returns users matching BOTH conditions |

#### Example Response
```json
[
  { "id": 2, "name": "Priya Singh",  "age": 30, "role": "ADMIN" },
  { "id": 7, "name": "Priya Sharma", "age": 30, "role": "USER"  }
]
```

---

### **Task 2 — Structured Data Submission API**

| Method | Endpoint   | Description              |
|--------|------------|--------------------------|
| POST   | `/submit`  | Submit structured data   |

#### Request Body (JSON)
```json
{
  "title":       "New Report",
  "description": "Monthly sales report",
  "submittedBy": "Rahul"
}
```

#### Responses

| Scenario          | Status | Response                                              |
|-------------------|--------|-------------------------------------------------------|
| Valid input       | 201    | `"Data submitted successfully by Rahul"`              |
| Empty title       | 400    | `"Invalid input for 'title': Title cannot be empty"`  |
| Empty description | 400    | `"Invalid input for 'description': ..."`              |
| Empty submittedBy | 400    | `"Invalid input for 'submittedBy': ..."`              |

---

### **Task 3 — Delete with Confirmation API**

| Method | Endpoint        | Description                      |
|--------|-----------------|----------------------------------|
| DELETE | `/users/{id}`   | Delete user with confirmation    |

#### Request Parameter

| Parameter | Type    | Required | Description               |
|-----------|---------|----------|---------------------------|
| `confirm` | Boolean | No       | Must be `true` to delete  |

#### Behavior

| URL | Result |
|-----|--------|
| `DELETE /users/1?confirm=true`  | ✅ Deletes user, returns success message |
| `DELETE /users/1?confirm=false` | ❌ Returns "Confirmation required"       |
| `DELETE /users/1` (no confirm)  | ❌ Returns "Confirmation required"       |
| `DELETE /users/99?confirm=true` | ❌ Returns 404 user not found            |

#### Example Responses
```json
// Success
"User with id 1 deleted successfully"

// Confirmation missing or false
"Confirmation required"
```

---

## ❌ Error Responses

All errors return this standard JSON format:
```json
{
  "status": 404,
  "message": "User not found with id: 99",
  "timestamp": "2024-01-10T10:30:00"
}
```

| Scenario              | Status | Message                                       |
|-----------------------|--------|-----------------------------------------------|
| User ID not found     | 404    | User not found with id: {id}                  |
| Negative age value    | 400    | Invalid input for 'age': Age must be positive |
| Empty title in submit | 400    | Invalid input for 'title': cannot be empty    |
| Any unexpected error  | 500    | Something went wrong: ...                     |

---

## 👥 Dummy Users (Pre-loaded Data)

| ID | Name          | Age | Role  |
|----|---------------|-----|-------|
| 1  | Rahul Sharma  | 25  | USER  |
| 2  | Priya Singh   | 30  | ADMIN |
| 3  | Amit Verma    | 30  | USER  |
| 4  | Sneha Patel   | 28  | USER  |
| 5  | Rohit Kumar   | 35  | ADMIN |
| 6  | Anjali Mehta  | 25  | USER  |
| 7  | Priya Sharma  | 30  | USER  |

---

## 🧠 Spring Concepts Demonstrated

### 1. `@RequestParam` with `required = false`
All 3 search parameters are optional. If not passed they come in as `null` and are simply skipped in the filter logic.
```java
@GetMapping("/search")
public List<User> searchUsers(
    @RequestParam(required = false) String  name,
    @RequestParam(required = false) Integer age,
    @RequestParam(required = false) String  role)
```

### 2. `@RequestBody`
Reads the JSON body from a POST request and converts it to a Java object automatically.
```java
@PostMapping("/submit")
public ResponseEntity<String> submitData(@RequestBody SubmitRequest request)
```

### 3. `@PathVariable`
Reads a value directly from the URL path like `/users/1` → id = 1.
```java
@DeleteMapping("/{id}")
public ResponseEntity<String> deleteUser(@PathVariable int id, ...)
```

### 4. Constructor Injection
Spring automatically provides dependencies through the constructor — no `@Autowired` on fields.
```java
public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
}
```

### 5. Stream Filtering
Users are filtered using Java Streams — clean, readable, and chainable.
```java
result = result.stream()
               .filter(u -> u.getName().equalsIgnoreCase(name))
               .collect(Collectors.toList());
```

### 6. ResponseEntity
Used to return custom HTTP status codes like `201 Created` and `400 Bad Request`.
```java
return ResponseEntity.status(HttpStatus.CREATED).body(message);
```

---

## 🗂️ Layered Architecture Flow

```
HTTP Request
     ↓
Controller   → Receives request, passes to service (no logic here)
     ↓
Service      → All filtering, validation, delete logic lives here
     ↓
Repository   → Handles get and delete on in-memory ArrayList
```

---

## 🧪 Testing the APIs (Postman)

**Get all users:**
```
GET http://localhost:8080/users/search
```

**Search by name (case-insensitive):**
```
GET http://localhost:8080/users/search?name=priya
```

**Search by age and role together:**
```
GET http://localhost:8080/users/search?age=30&role=USER
```

**Submit valid data → 201:**
```
POST http://localhost:8080/submit
Content-Type: application/json

{
  "title": "New Report",
  "description": "Monthly sales report",
  "submittedBy": "Rahul"
}
```

**Submit empty title → 400:**
```
POST http://localhost:8080/submit
Content-Type: application/json

{
  "title": "",
  "description": "Some data",
  "submittedBy": "Rahul"
}
```

**Delete with confirmation → success:**
```
DELETE http://localhost:8080/users/1?confirm=true
```

**Delete without confirmation → blocked:**
```
DELETE http://localhost:8080/users/1?confirm=false
```

**Delete non-existing user → 404:**
```
DELETE http://localhost:8080/users/99?confirm=true
```

---

## 👨‍💻 Author

**Deevyanshu Tiwari**