# Employee Management System (Java • Swing • JDBC)

A simple desktop app to manage employees with a GUI. It supports Add, View, Update, and Delete operations backed by a relational database.

## Project structure

- `src/Main.java` — application entry point (launches the GUI).
- `src/gui/*.java` — Swing UI panels:
  - `AddEmployeePanel`, `ViewEmployeePanel`, `UpdateEmployeePanel`, `DeleteEmployeePanel`, `MainFrame`, `BasePanel`
- `src/Operations/*.java` — application logic and database operations:
  - `DatabaseConnection`, `EmployeeAdd`, `EmployeeShow`, `EmployeeUpdate`, `RemoveEmployee`, `EmployeeInfo`, `MainMenu`, `Exit`
- `lib/mysql-connector-j-9.4.0.jar` — MySQL JDBC driver.


## Prerequisites

- JDK 17+ installed and on PATH.
- MySQL Server 8.x running locally (or reachable remotely).
- IDE - IntelliJ IDEA (recommended).
- Operating System (project tested on Windows).

## Database setup (MySQL)

1. Create a database and table:
   ```sql
   CREATE DATABASE IF NOT EXISTS ems DEFAULT CHARACTER SET utf8mb4;

   USE ems;

   CREATE TABLE IF NOT EXISTS employees (
     employee_id VARCHAR(50) PRIMARY KEY,
     name        VARCHAR(200) NOT NULL,
     contact     VARCHAR(100),
     email       VARCHAR(200),
     position    VARCHAR(100),
     salary      VARCHAR(100)
   );
   

©Naeshby