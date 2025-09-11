# 📝 Tasks Management Application  
Final Project – Design Patterns (Java, Swing, DerbyDB)

![Java](https://img.shields.io/badge/Java-24-orange?logo=java&logoColor=white)
![Swing](https://img.shields.io/badge/UI-Swing-blue)
![Database](https://img.shields.io/badge/DB-Derby-lightgrey)
![License](https://img.shields.io/badge/License-MIT-green)

---

## 📖 Overview
This project is a **stand-alone Java desktop application** for managing tasks.  
It was developed as part of the **Design Patterns** course and demonstrates usage of multiple patterns:  

- 🧩 **Strategy** – Sorting tasks (by ID, Title, State).  
- 🔍 **Combinator** – Flexible filtering & searching.  
- 👁️ **Observer** – Updates the UI table when data changes.  
- 🗄️ **DAO** – Derby database access and persistence.  
- 📊 **Visitor** – Generating reports with task statistics.  

---

## ✨ Features
✔️ Add / Edit / Delete tasks (including multi-row delete)  
✔️ Sort & Filter by state (ToDo, In Progress, Completed)  
✔️ 🔎 Search tasks by title  
✔️ Export tasks to CSV  
✔️ 📑 Generate summary reports with counts and task list  
✔️ Modernized **Swing UI** (custom font, look & feel)  

---

## 📂 Project Structure
src/
└── il/ac/hit/project/main/
    ├── model/
    │   ├── dao/        # Data Access Objects (Derby integration)
    │   ├── report/     # ReportVisitor + ReportData
    │   └── task/       # Task, TaskState, ITask
    ├── view/           # MainFrame + dialogs (Swing UI)
    └── viewmodel/      # TasksViewModel, Strategy, Combinator

