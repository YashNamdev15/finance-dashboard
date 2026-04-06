# 💰 Finance Dashboard Backend

A scalable and secure RESTful backend built using Spring Boot for managing financial records with role-based access control and advanced dashboard analytics.

---

## 🚀 Overview

This project simulates a real-world finance dashboard system where different users interact with financial data based on their roles. It demonstrates clean backend architecture, efficient data handling, and secure access control.

---

## ✨ Key Features

- 🔐 JWT-based Authentication & Authorization
- 👥 Role-Based Access Control (Viewer, Analyst, Admin)
- 💰 Financial Records Management (Create, Read, Update, Delete)
- 🔍 Advanced Filtering & Search APIs
- 📊 Dashboard Analytics:
  - Total Income & Expenses
  - Net Balance Calculation
  - Category-wise Spending
  - Monthly Trends
  - User-wise Financial Summary (Admin/Analyst)
- ⚡ Optimized Database Queries using Aggregation (SUM, GROUP BY)

---

## 🧠 Roles & Permissions

### 🟡 Viewer
- Can create and manage own financial records
- Can view personal dashboard data

### 🔵 Analyst
- Can view all financial records
- Access advanced analytics and insights

### 🔴 Admin
- Full access to all records and users
- Can view system-wide insights and user-level summaries

---

## 🛠️ Tech Stack

- **Backend Framework:** Spring Boot
- **Language:** Java 17
- **Security:** Spring Security + JWT
- **Database:** MySQL
- **ORM:** Spring Data JPA (Hibernate)
- **API Documentation:** Swagger (OpenAPI), Postman
- **Build Tool:** Maven

---

## 📂 API Endpoints

### 🔐 Authentication
- `POST /auth/register` → Register a new user
- `POST /auth/login` → Authenticate and receive JWT token

### 💰 Financial Records
- `POST /api/record/create` → Create a record
- `GET /api/record/loggedInUser` → Get user's records
- `PUT /api/record/update/{id}` → Update record
- `DELETE /api/record/{id}` → Delete record

### 🔍 Filtering
- `GET /api/record/filter` → Filter records by type, category, or date

### 📊 Dashboard
- `GET /api/dashboard` → Role-based dashboard data
- `GET /api/dashboard/monthly-trend` *(optional)* → Trend analysis

---

## 📄 API Documentation

### 🔹 Swagger UI
