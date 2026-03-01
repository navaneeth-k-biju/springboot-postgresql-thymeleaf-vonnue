# Decision Companion System

A structured decision-making web application built using Spring Boot, Thymeleaf, Spring Security (JWT), JPA, and PostgreSQL.

This system helps users evaluate multiple options against weighted criteria and receive a ranked recommendation with a transparent explanation.

## 1. My Understanding of the Problem

Real-world decisions are often made emotionally or impulsively. People struggle to:

- Compare multiple options objectively
- Prioritize what truly matters
- Understand why one option is better than another
- Avoid bias in complex decisions

The goal of this system is to:

1. Allow users to define a decision.
2. Add multiple options.
3. Define evaluation criteria with different importance (weights).
4. Score each option against each criterion.
5. Compute a weighted result.
6. Provide a ranked recommendation.
7. Explain the reasoning behind the recommendation.

The system must work without relying on AI, meaning the evaluation logic must be deterministic and transparent.

## 2. Assumptions Made

While designing this system, I made the following assumptions:

- Each decision belongs to one user.
- Each decision can have:

	- Multiple options
	- Multiple criteria

- Each criterion has a weight between 1 (Very Low) and 5 (Very High).
- Each option is scored from 1 to 10 per criterion.
- Weighted scoring formula:
	totalScore = Σ (criteriaWeight × userScore)
- Maximum possible score per option:
	maxPossibleScore = Σ (criteriaWeight × 10)

## 3. Why I Structured the Solution This Way

I followed a layered architecture:
	Controller → Service → Repository → Database
### Entities

The domain model reflects real-world decision logic:
- AppUser
- Decision
- DecisionOption
- Criteria
- DecisionScore

### Service Layer

All business logic is inside services:

- DecisionService → CRUD + validation + ownership checks
- EvaluationService → weighted ranking logic
- UserService → registration + authentication integration

### Stateless JWT Authentication

Instead of sessions, I used:

- JWT stored in HttpOnly cookie
- Stateless configuration

This improves scalability and reflects modern REST-style security.

## 4. Design Decisions and Trade-offs

### 1. Separate DecisionScore Entity

Instead of storing scores inside DecisionOption, I created a dedicated entity. Because scoring is a many-to-many relationship between options and criteria.

This avoids:

- Data duplication
- Poor schema design
- Hard-to-query nested structures

Trade-off: Slightly more complex save logic.

### 2. Ownership Validation at Service Layer

Each decision retrieval uses:
'findByIdAndOwnerId()'

This ensures:

- A user cannot access another user’s decisions.
- Security is enforced at business level, not just UI.

Trade-off: Extra queries, but improved security.

### 3. Weighted Scoring (Deterministic Logic)

Instead of using AI or heuristics:

- Pure mathematical weighted model
- Fully explainable scoring
- Transparent breakdown

This improves trust and academic validity.

### 4. Stateless Security

'SessionCreationPolicy.STATELESS'
Benefits:

- Scalable
- Modern architecture
- No server-side session storage

Trade-off:

- Slightly more complex implementation (JWT parsing)

## 5. Edge Cases Considered

### 1. User Isolation

- Users cannot access other users' decisions.
- Verified using ownerId.

### 2. Empty Options or Criteria

In showScoreForm():
'if (decision.getOptions().isEmpty() || decision.getCriteriaList().isEmpty())'

Prevents scoring without proper setup.

### 3. Deleting Options

Before deleting:
'boolean optionInDecision = decision.getOptions().stream()'
Prevents deleting options not belonging to that decision.

### 4. Duplicate Scores

Before saving new scores:
'scoreRepository.deleteByDecisionOptionId(optionId);'
Ensures re-scoring replaces previous values cleanly.

### 5. Invalid Login

Authentication exceptions are caught and redirected with proper message.

6. Expired JWT

If token parsing fails:
'SecurityContextHolder.clearContext();'
User is redirected to login.

## 6. How to Run the Project
### Prerequisites

- Java 17+
- Maven
- PostgreSQL
- IDE (STS / IntelliJ)

### Step 1: Clone the Project

'''
git clone <repository-url>
cd decision-companion
'''

### Step 2: Configure Database
Create database:
'CREATE DATABASE decision_companion;'

Update application.properties:
'''
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.url=${DB_JDBC_URL:jdbc:postgresql://${DB_HOST:localhost}:${DB_PORT:5432}/${DB_NAME:decision_db}?sslmode=${DB_SSL_MODE:require}}
spring.datasource.username=your_username
spring.datasource.password=your_password

spring.thymeleaf.prefix=classpath:/templates/
spring.thymeleaf.suffix=.html
spring.thymeleaf.cache=false 

spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
'''

### Step 3: Run the Application
mvn spring-boot:run
Or run from IDE

### Access the Application

http://localhost:8080

Register → Login → Create Decision → Add Options & Criteria → Score → View Recommendation.

## 7. What I Would Improve With More Time

### 1. Visualization Enhancements

- Radar chart per option
- Bar graph comparison
- Weight distribution visualization

### 2. Role-Based Access

Add:

- Admin role
- Analytics dashboard

### 3. AI-Assisted Suggestions (Optional Extension)

AI could:

- Suggest criteria based on decision type
- Recommend typical weight distributions
