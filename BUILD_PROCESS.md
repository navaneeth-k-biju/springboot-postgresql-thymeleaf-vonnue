#  Build Process – Decision Companion System

This document captures how the Decision Companion System evolved from a basic idea into a structured, secure, and explainable decision-making application.  

It reflects how I started, how my thinking changed over time, the changes I considered, refactoring decisions I made, mistakes I encountered, and what evolved during development.

---

# 1. How I Started

I started with a simple question:

> How can I build a system that helps someone make a rational decision instead of an emotional one?

Initially, I approached it as a basic CRUD application:

- Create a decision  
- Add options  
- Add criteria  
- Store data in the database  

My early focus was:
- Designing entities  
- Making relationships work  
- Saving and retrieving data correctly  

At this stage, the goal was simply:

> “Can I store a decision and retrieve it properly?”

Once that worked, I began thinking beyond CRUD.

---

#  2. How My Thinking Evolved

## Phase 1 – Data Storage Mindset

At first, I thought in terms of tables:

- Decision  
- Option  
- Criteria  

But I quickly realized the real challenge wasn’t storing data — it was modeling relationships correctly.

The key realization was:

> Each option must be scored against each criterion.

That meant this was not just separate tables. It was a structured matrix.

---

##  Phase 2 – Modeling the Decision Matrix

I identified that scoring represents a **many-to-many relationship with additional data (the score value).**

Instead of embedding scores inside options or criteria, I introduced a separate entity:

DecisionScore

- option_id
- criteria_id
- score

This design:

- Normalized the schema
- Avoided data duplication
- Made queries clean
- Scaled properly

This was the turning point in the architecture.

---

##  Phase 3 – From “Working” to “Secure”

Initially, I focused on functionality.  
Then I asked:

- What stops one user from accessing another user’s decisions?
- What if someone manually changes a URL?

This led to ownership validation at the service layer:

findByIdAndOwnerId()

This ensured:

- Users can only access their own decisions.
- Security is enforced at the business logic layer.

The system shifted from being functional to being secure.

---

##  Phase 4 – From Basic App to Structured System

Once the core worked, I improved:

- Stateless JWT authentication
- Global exception handling
- Layered architecture (Controller → Service → Repository)
- Defensive validation
- Transaction management

---

# 3. Alternative Approaches Considered

##  1. Storing Scores Inside DecisionOption

I considered storing scores inside the option entity using a Map or JSON structure.

Rejected because:

- Not normalized
- Hard to query
- Breaks relational modeling principles
- Difficult to scale

Creating `DecisionScore` was the correct long-term design choice.

---

##  2. Using Session-Based Authentication

Spring Security sessions would have been easier to implement.

I chose JWT instead because:

- Stateless architecture
- Better scalability
- More aligned with modern backend design
- Better learning opportunity

Trade-off: Increased implementation complexity.

---

##  3. Putting Evaluation Logic in Controller

At one stage, I nearly calculated scores directly inside the controller.

I moved the logic into:

EvaluationService

This improved:

- Separation of concerns
- Maintainability
- Clean controller design

---

# 4. Refactoring Decisions

##  Refactor 1 – Ownership Validation

Initially:

findById(id)

Refactored to:

findByIdAndOwnerId(id, userId)

Reason:
Security must not rely on frontend restrictions.

---

##  Refactor 2 – Preventing Duplicate Scores

Originally, re-scoring could create duplicate entries.

I added logic to delete previous scores before saving new ones.

Reason:
Re-scoring should overwrite, not accumulate.

---

##  Refactor 3 – Enhanced Result Presentation

Originally:
- Only raw total score was shown.

Improved version includes:
- Maximum possible score
- Percentage calculation
- Ranked ordering
- Detailed explanation breakdown
- Human-readable summary

This made the output more transparent and user-friendly.

---

##  Refactor 4 – Global Exception Handling

Instead of letting exceptions propagate directly to the user, I added:

- `GlobalExceptionHandler`

Now:
- Errors are gracefully handled
- Users are redirected properly
- Messages are meaningful

---

# 5. Mistakes and Corrections

##  Mistake 1 – Not Thinking About Security Early

Initially, functionality was prioritized over security.

Correction:
- Ownership validation added
- Endpoint protection enforced
- Stateless authentication implemented

---

##  Mistake 2 – Not Handling Empty Scoring Cases

Scoring page could load without options or criteria.

Correction:
Added validation to prevent scoring without required setup.

---

##  Mistake 3 – Architecture Drift

As the project grew, logic began crowding controllers.

Correction:
Strict separation into:

- Controller layer
- Service layer
- Repository layer
- DTOs

---

# 6. What Changed During Development and Why

| Early Stage | Final Stage | Why It Changed |
|-------------|-------------|---------------|
| Simple CRUD app | Structured evaluation engine | Needed proper modeling |
| Basic authentication idea | JWT-based stateless security | Scalability + modern design |
| Direct ID retrieval | Owner-validated retrieval | Security |
| Raw score display | Ranked, percentage-based explanation | Transparency |
| Minimal validation | Defensive validation | Robustness |
| Functional focus | Structured architecture focus | Maintainability |

---
