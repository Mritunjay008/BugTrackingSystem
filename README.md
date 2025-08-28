# 🐞 Bug Tracking System  

A **Spring Boot based backend application** to manage software bugs within projects. It provides role-based functionality for **Admin, Tester, and Developer** to collaborate on reporting, assigning, and resolving bugs efficiently.  

---

## 🚀 Features
- **User Roles**
  - **Admin**: Create and manage projects.  
  - **Tester**: Report bugs, assign them to developers.  
  - **Developer**: Resolve bugs assigned to them.  

- **Bug Management**
  - Create, update, delete bugs.  
  - Manage bug status: `OPEN → IN_PROGRESS → FIXED → CLOSED`.  
  - Manage bug severity: `LOW`, `MEDIUM`, `HIGH`, `CRITICAL`.  
  - Filter bugs by Project, Developer, or Status.  

- **Project Management**
  - Admin creates projects.  
  - Developers and Testers can join projects.  

- **Database**
  - PostgreSQL for persistence.  
  - JPA/Hibernate for ORM.  

---

## 🏗️ Architecture
- **Spring Boot** (REST APIs)  
- **JPA/Hibernate** (ORM)  
- **PostgreSQL** (Database)  
- **ModelMapper** (Entity ↔ DTO conversion)  

---

## 🔄 Flow of Application
1. **User Registration & Login** → User chooses role (Admin, Tester, Developer).  
2. **Project Management** → Admin creates projects, others can join.  
3. **Bug Reporting (Tester)** → Report bugs, assign to developers.  
4. **Bug Resolution (Developer)** → Update status after fixing.  
5. **Tracking** → View bugs by project, developer, or status.  

---

## ⚙️ Installation & Setup

### 1️⃣ Clone the Repository
```bash
git clone https://github.com/your-username/bug-tracking-system.git
cd bug-tracking-system
```

### 2️⃣ Configure Database
- Create PostgreSQL database:
```sql
CREATE DATABASE bugtrackingsystem;
CREATE USER buguser WITH PASSWORD 'bugpass';
GRANT ALL PRIVILEGES ON DATABASE bugtrackingsystem TO buguser;
```

### 3️⃣ Update `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bugtrackingsystem
spring.datasource.username=buguser
spring.datasource.password=bugpass

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

### 4️⃣ Run the Application
```bash
mvn spring-boot:run
```

Application will start on:  
👉 `http://localhost:8080`

---

## 📡 API Endpoints (Sample)
- `POST /api/bugs` → Create new bug  
- `PUT /api/bugs/{id}/status` → Update bug status  
- `PUT /api/bugs/{id}/severity` → Update severity  
- `DELETE /api/bugs/{id}` → Delete bug  
- `GET /api/bugs/project/{projectId}` → Get bugs by project  
- `GET /api/bugs/developer/{developerId}` → Get bugs by developer  

---

Run tests:
```bash
mvn test
```

---

## 📸 Project Workflow Example
1. Admin creates a project → `E-commerce App`.  
2. Tester reports a bug → *"Login button not working"* (Severity: HIGH).  
3. Tester assigns bug to Developer.  
4. Developer updates status → `IN_PROGRESS → FIXED`.  
5. Bug history stored for tracking.  

---

## 📌 Future Enhancements
- JWT-based Authentication & Authorization.  
- Web UI for bug tracking.  
- Email/SMS notifications on bug status updates.  
- Advanced analytics for bug trends.  

---

## 👨‍💻 Author
**Mritunjay Kumar**  
Bug Tracking System – Backend built with Spring Boot & PostgreSQL.  
