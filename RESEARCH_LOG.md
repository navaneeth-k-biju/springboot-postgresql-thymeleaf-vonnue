#  Research Log – Decision Companion System

This document contains the research and prompt history that influenced the development of the Decision Companion System.

Contents:

- AI prompts used throughout development
- Search queries performed (Google and documentation searches)
- References that influenced architectural decisions
- What I accepted, rejected, or modified from AI-generated outputs

---

#  All AI Prompts Used


1. "Explain Decision Companion System."

2. "Take me through its advantages and disadvantages."

3. "How do i integrate api into a springboot project."

4. "Free api's provided."

5. "Explain how to model many-to-many relationship with additional attributes in JPA."

6. "What is the best way to structure entities for a decision scoring system?"

7. "How to prevent one user from accessing another user's data in Spring Boot?"

8. "How to map OneToMany and ManyToOne relationships correctly in JPA?"

9. "Difference between cascade types in JPA."

10. "Why use orphanRemoval=true?"

11. "How to create foreign key in MySQL Workbench?"

12. "Should I store scoring matrix as JSON or relational table?"

13. "How to delete child entities when parent is deleted?"

14. "How to implement JWT authentication in Spring Boot."

15. "Difference between session-based and JWT authentication."

16. "How to store JWT in HttpOnly cookie securely."

17. "How to configure SecurityFilterChain in Spring Boot 3."

18. "Why use stateless session policy?"

19. "How to extract JWT from cookie instead of Authorization header."

20. "How to clear security context if token is invalid."

21. "How to implement weighted scoring algorithm in Java."

22. "How to calculate weighted average with different importance levels."

23. "How to rank list based on computed score."

24. "How to compute percentage based on maximum possible score."

25. "How to generate explanation summary for ranked results."

26. "How to move business logic from controller to service layer."

27. "How to implement global exception handler in Spring Boot."

28. "How to prevent duplicate inserts when updating related entities."

29. "Best practices for transactional methods in Spring."

30. "When to use @Transactional."

31. "How to bind list inputs in Thymeleaf form."

32. "How to send multiple hidden inputs in Thymeleaf."

33. "How to handle dynamic scoring matrix in Thymeleaf."

34. "How to format decimal numbers in Thymeleaf."

35. "How to show conditional content in Thymeleaf."

36. "which database is easier to host"

37. "git stash pop?"

38. "why do we use transactional"

39. "how to fix many to many relationship problem"

---

# 2. Google Search Queries Used

Below are the main google search queries conducted during development.

- "Render hosting"

- "Spring Boot 3 JWT authentication example"

- "SecurityFilterChain configuration example"

- "HttpOnly cookie JWT Spring Boot"

- "jwt application properties for springboot"

- "Thymeleaf dynamic table form example"

- "Thymeleaf multiple input same name list binding"

- "Thymeleaf format decimal number"

- "PostgreSQL application properties in springboot"

- "Layered architecture Spring Boot example"

- "Spring Boot @Transactional explanation"

---

# 3. What I Accepted from AI Outputs

The following were directly accepted:

- Explanation of many-to-many with extra attributes → led to DecisionScore entity.

- JWT filter structure.

- SecurityFilterChain configuration pattern.

- Weighted scoring formula structure.

- Ranking via sorting comparator.

- Global exception handler pattern.

- DTO-based result formatting approach.


---

# 4. What I Rejected from AI Outputs

I rejected suggestions that:

- Stored scoring matrix as JSON.

- Used Map structures inside entity classes.

- Mixed business logic inside controllers.

- Skipped ownership validation.

- Used overly complex role hierarchies.

- Relied entirely on AI for decision logic explanation.

---

# 5. What I Modified from AI Outputs

Many AI outputs were not used directly, but adapted.

Examples:

---

##  JWT Handling

AI suggested:
- Using Authorization header.

I modified:
- Extract JWT from HttpOnly cookie.

- Maintain stateless configuration.

- Add graceful failure handling.

---

##  Evaluation Logic

AI suggested:
- Simple weighted sum.

I enhanced:
- Percentage calculation.

- Maximum possible score calculation.

- Explanation lines per criterion.

- Summary generation for ranking.

---

##  Security

AI suggested:
- Basic findById validation.

I modified:
- Implemented findByIdAndOwnerId.

- Enforced ownership at service layer.

