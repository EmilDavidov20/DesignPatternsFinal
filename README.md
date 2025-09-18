# 📝 Tasks Management Application

Final Project - Design Patterns (Java, Swing, DerbyDB)

![Java](https://img.shields.io/badge/Java-24-orange?logo=java&logoColor=white)
![Swing](https://img.shields.io/badge/UI-Swing-blue)
![Database](https://img.shields.io/badge/DB-Derby-lightgrey)
![License](https://img.shields.io/badge/License-MIT-green)

---

## 📖 Overview

This project is a **stand-alone Java desktop application** for managing tasks.  
It was developed as part of the **Design Patterns** course and demonstrates usage of multiple patterns:

- 🧩 **Strategy** - Sorting tasks (by ID, Title, State).
- 🔍 **Combinator** - Flexible filtering & searching.
- 👁️ **Observer** - Updates the UI table when data changes.
- 🗄️ **DAO** - Derby database access and persistence.
- 📊 **Visitor** - Generating reports with task statistics.

---

## ✨ Features

✔️ Add / Edit / Delete tasks
✔️ Sort & Filter by state (ToDo, In Progress, Completed)  
✔️ 🔎 Search tasks by title  
✔️ Export tasks to CSV  
✔️ 📑 Generate summary reports with counts and task list  
✔️ Modernized **Swing UI**

---

## 🛠️ Technologies

- Java 24
- Swing
- Apache DerbyDB
- JUnit 5

---

## 🚀 How to Run the Project

Follow these steps to download, set up, and run the application locally:

### 1️⃣ Clone the repository

```bash
git clone https://github.com/EmilDavidov20/DesignPatternsFinal
cd DesignPatternsFinal
```

---

### 2️⃣ Open in IntelliJ IDEA Configure Libraries

Launch IntelliJ IDEA (Community or Ultimate).
Click File > Open... and select the DesignPatternsFinal folder.

---

### 3️⃣ Configure Libraries

Go to File > Project Structure > Libraries.
Add the JARs from the /lib folder...

---

### 4️⃣ Database Setup

The app uses Apache Derby Embedded.
A local database folder tasksdb is already included in the project.
Derby will automatically boot and create/update the database when running the app.

---

### 5️⃣ Run the Application

Locate the main class:

```bash
il.ac.hit.project.main.Main
```

Right-click it - 'Main' → Run.
The Task Manager UI should appear. 

