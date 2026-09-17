# Unit and API Testing in Spring Boot

A Spring Boot project focused on learning and practicing **Unit Testing** and **API/Controller Testing** using **JUnit 5, Mockito, and Spring Boot Test**.

The project uses a simple **Product API** to demonstrate how different layers of a Spring Boot application can be tested independently.

## 📌 Project Overview

This project was created to understand how testing works in a real Spring Boot application.

It covers:

* Unit testing service-layer logic
* Mocking dependencies with Mockito
* Verifying repository interactions
* Testing REST controllers
* Testing HTTP requests and responses
* Testing success and failure scenarios
* Using `MockMvc` for controller/API testing
* Using JUnit 5 assertions and test lifecycle

The main application is a simple **Product Management REST API**.

---

## 🛠️ Tech Stack

* **Java**
* **Spring Boot**
* **Spring Web**
* **Spring Data JPA**
* **JUnit 5**
* **Mockito**
* **MockMvc**
* **Maven**
* **MySQL**

---

## 📂 Project Structure

```text
src
├── main
│   └── java
│       └── com.unit.testing
│           ├── controller
│           │   └── ProductController.java
│           │
│           ├── model
│           │   └── Product.java
│           │
│           ├── repository
│           │   └── ProductRepository.java
│           │
│           └── service
│               └── ProductService.java
│
└── test
    └── java
        └── com.unit.testing
            ├── controller
            │   └── ProductControllerTest.java
            │
            └── service
                └── ProductServiceTest.java
```

---

# 🧪 Testing

The project focuses on testing two major layers:

```text
                 Product API
                      │
                      ▼
              ProductController
                      │
                      ▼
                ProductService
                      │
                      ▼
              ProductRepository
```

Each layer can be tested independently by mocking the dependencies it relies on.

---

## 1. Unit Testing

Unit tests focus on testing a single class or piece of logic in isolation.

For example:

```text
ProductService
      │
      └── ProductRepository → Mock
```

Mockito is used to create a mock repository so that the service can be tested without accessing the actual database.

### Example

```java
when(productRepository.findById(99L))
        .thenReturn(Optional.empty());
```

This tells Mockito:

> When the service asks the repository for product `99`, pretend that the product does not exist.

The service can then be tested against the expected behavior.

---

## 2. Mockito

Mockito is used to mock dependencies and verify interactions.

### Stubbing

```java
when(productRepository.findById(1L))
        .thenReturn(Optional.of(product));
```

### Verification

```java
verify(productRepository).findById(1L);
```

This verifies that the repository method was actually called.

Mockito helps keep unit tests independent from external dependencies such as databases.

---

# 🌐 REST API

The application exposes the following endpoints.

| Method | Endpoint         | Description         |
| ------ | ---------------- | ------------------- |
| `POST` | `/products`      | Add a product       |
| `GET`  | `/products`      | Get all products    |
| `GET`  | `/products/{id}` | Get a product by ID |

---

## Add Product

### Request

```http
POST /products
Content-Type: application/json
```

```json
{
    "name": "Laptop",
    "price": 75000,
    "quantity": 10
}
```

---

## Get All Products

```http
GET /products
```

Example response:

```json
[
    {
        "id": 1,
        "name": "Laptop",
        "price": 75000,
        "quantity": 10
    },
    {
        "id": 2,
        "name": "Mouse",
        "price": 1000,
        "quantity": 25
    }
]
```

---

## Get Product By ID

```http
GET /products/1
```

Example response:

```json
{
    "id": 1,
    "name": "Laptop",
    "price": 75000,
    "quantity": 10
}
```

---

# 🎯 Controller Testing

The controller tests use:

* `@WebMvcTest`
* `MockMvc`
* `Mockito`
* `ObjectMapper`
* `JUnit 5`

The controller's service dependency is mocked:

```java
@MockBean
private ProductService productService;
```

This allows the controller to be tested without running the complete application or connecting to the database.

### Example

```java
mockMvc.perform(get("/products/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("Laptop"));
```

This test verifies:

1. The `/products/1` endpoint can be called.
2. The response status is `200 OK`.
3. The returned JSON contains the expected product.

---

# ✅ Test Scenarios

The project includes tests for both successful and failure scenarios.

### Product Service

* Successfully add a product
* Prevent duplicate products
* Retrieve all products
* Retrieve a product by ID
* Throw an exception when a product does not exist

### Product Controller

* Successfully add a product
* Retrieve all products
* Retrieve a product by ID
* Handle a product-not-found scenario
* Handle an error while adding a product

---

# ▶️ Running the Project

### 1. Clone the repository

```bash
git clone https://github.com/NisargMakwana142/Unit_And_Api_Testing_In_SpringBoot.git
```

### 2. Open the project

Open the project in IntelliJ IDEA or another Java IDE.

### 3. Configure the database

Update the database configuration in:

```text
src/main/resources/application.properties
```

according to your local MySQL setup.

### 4. Run the application

Using Maven:

```bash
mvn spring-boot:run
```

Or using the Maven Wrapper:

**Windows**

```bash
mvnw.cmd spring-boot:run
```

**Linux/macOS**

```bash
./mvnw spring-boot:run
```

---

# 🧪 Running Tests

Run all tests with:

```bash
mvn test
```

Or with the Maven Wrapper:

**Windows**

```bash
mvnw.cmd test
```

**Linux/macOS**

```bash
./mvnw test
```

You can also run individual test classes directly from IntelliJ IDEA.

---

# 📚 What I Learned

Through this project, I practiced:

* Writing unit tests with JUnit 5
* Understanding the Arrange → Act → Assert pattern
* Using Mockito for mocking
* Stubbing method calls with `when().thenReturn()`
* Verifying interactions with `verify()`
* Testing exceptions with `assertThrows()`
* Testing Spring MVC controllers
* Using `MockMvc`
* Testing HTTP status codes
* Testing JSON responses with `jsonPath`
* Separating unit tests from API/controller tests
* Understanding why dependencies are mocked during unit testing

---

## 🔄 Testing Flow

```text
                UNIT TESTING

                  JUnit 5
                     │
                     ▼
               ProductService
                     │
                     ▼
                Mockito Mock
                     │
                     ▼
              ProductRepository
```

```text
                API TESTING

                  JUnit 5
                     │
                     ▼
                  MockMvc
                     │
                     ▼
              ProductController
                     │
                     ▼
               Mocked Service
```

---

## 🚀 Future Improvements

Possible additions to this project:

* Integration testing with `@SpringBootTest`
* Repository testing with `@DataJpaTest`
* Full API integration tests
* Testcontainers
* Validation testing
* Global exception handling tests
* Code coverage with JaCoCo
* CI testing with GitHub Actions

---

## 👨‍💻 Author

**Nisarg Makwana**

GitHub: [NisargMakwana142](https://github.com/NisargMakwana142)

---

## 📄 License

This project is created for learning and educational purposes.
