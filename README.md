# 📚 LibraryOS — Library Management System

![Java](https://img.shields.io/badge/Java-Jakarta_EE-ED8B00?logo=openjdk&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-Database-4479A1?logo=mysql&logoColor=white)
![Tomcat](https://img.shields.io/badge/Apache-Tomcat_10+-F8DC75?logo=apachetomcat&logoColor=black)
![License](https://img.shields.io/badge/License-MIT-22c55e)
![Status](https://img.shields.io/badge/Status-Active-22c55e)

> A role-based library management platform built using Java Servlets, JDBC, MySQL, and JavaScript.

---

## 📌 Table of Contents

1. [Overview](#overview)
2. [Key Highlights](#key-highlights)
3. [Features](#features)
4. [Tech Stack](#tech-stack)
5. [System Architecture](#system-architecture)
6. [Project Structure](#project-structure)
7. [Getting Started](#getting-started)
8. [Configuration](#configuration)
9. [Deployment](#deployment)
10. [Security](#security)
11. [User Roles](#user-roles)
12. [Known Limitations](#known-limitations)
13. [Future Enhancements](#future-enhancements)
14. [Contributing](#contributing)
15. [Testing](#testing)
16. [Screenshots](#screenshots)
17. [Live Demo](#live-demo)
18. [License](#license)

---

## 🗺️ Overview

**LibraryOS** is a web-based library management system with two dedicated portals — Admin and Member. It handles the complete library lifecycle from book cataloguing through member management, book issuance, returns, overdue tracking, and fine calculation — all from a responsive, session-secured web interface.

---

## 🔥 Key Highlights

- Role-based authentication (Admin / Member)
- Full book lifecycle management (Add → Issue → Return → Fine Calculation)
- Member self-registration with admin-controlled access
- Dynamic fine calculation based on configurable penalty rates
- AJAX-driven interactions for seamless, no-reload UI updates
- Session-based access control across all panels
- Responsive dual-role dashboard system

---

## ✨ Features

### Admin Panel
- Dashboard with live statistics — total books, members, active issues, overdue count
- Book management — add, edit, delete, and browse inventory
- Member management — add, update, and view member profiles
- Issue books to members with automated tracking
- Return processing with automatic fine calculation
- View all issued books and overdue records
- Configure system settings (penalty rate per day)

### Member Portal
- Self-registration and login
- Personal dashboard with issued books and return history
- View overdue status and pending fines
- Request book issuance via a guided form
- Access profile and activity summary

---

## 🛠️ Tech Stack

| Layer          | Technology                                     |
|----------------|------------------------------------------------|
| Backend        | Java Servlets (Jakarta EE / Servlet API 6.x)  |
| Database       | MySQL 8+ via JDBC                              |
| Frontend       | HTML5, CSS3, JavaScript (ES6)                  |
| Async          | AJAX (XMLHttpRequest for dynamic updates)      |
| Server         | Apache Tomcat 10+                              |
| Build Tool     | Apache Ant (`build.xml`)                       |
| Auth           | Java HttpSession (role-based, server-side)     |
| Styling        | Custom CSS                                     |

---

## 🏗️ System Architecture

```
        ┌──────────────────────┐
        │    Member Portal     │
        └──────────┬───────────┘
                   │
                   ▼
        ┌──────────────────────┐
        │   Book Issue Engine  │
        └──────────┬───────────┘
                   │
                   ▼
        ┌──────────────────────┐
        │  Return & Fine Calc  │
        └──────────┬───────────┘
                   │
                   ▼
        ┌──────────────────────┐
        │  Overdue Tracker     │
        └──────────┬───────────┘
                   │
                   ▼
        ┌──────────────────────┐
        │  Admin Dashboard     │
        └──────────────────────┘
```

| Module               | Responsibility                                              |
|----------------------|-------------------------------------------------------------|
| Member Portal        | Self-registration, issued books view, fine status           |
| Admin Panel          | Book & member management, issue/return, system settings     |
| Book Issue Engine    | Matching members to available books, issue tracking         |
| Return & Fine Calc   | Return processing with configurable daily penalty rates     |
| Overdue Tracker      | Identifies overdue records and calculates outstanding fines |

---

## 📂 Project Structure

```
LibraryManagementSystem/
│
├── src/
│   └── java/
│       ├── InitDB.java                     # Database initializer
│       └── com/finlogic/
│           ├── config/
│           │   └── DBConnection.java       # ⚠️ DB credentials (not tracked)
│           │
│           ├── model/
│           │   ├── Book.java
│           │   ├── Member.java
│           │   └── IssueBook.java
│           │
│           ├── dao/
│           │   ├── BookDAO.java
│           │   ├── MemberDAO.java
│           │   ├── IssueBookDAO.java
│           │   └── SettingsDAO.java
│           │
│           └── servlet/
│               ├── LoginServlet.java
│               ├── LogoutServlet.java
│               ├── RegisterServlet.java
│               ├── DashboardDataServlet.java
│               ├── AddBookServlet.java
│               ├── ViewBookServlet.java
│               ├── ViewBookByIdServlet.java
│               ├── EditBookServlet.java
│               ├── UpdateBookServlet.java
│               ├── DeleteBookServlet.java
│               ├── AddMemberServlet.java
│               ├── ViewMemberServlet.java
│               ├── ViewMemberByIdServlet.java
│               ├── EditMemberServlet.java
│               ├── UpdateMemberServlet.java
│               ├── IssueBookServlet.java
│               ├── IssuedBookServlet.java
│               ├── IssuedBookInfoServlet.java
│               ├── ReturnBookServlet.java
│               ├── ConfirmReturnServlet.java
│               ├── UserIssueBookFormServlet.java
│               └── SettingsServlet.java
│
├── web/
│   ├── WEB-INF/
│   │   └── web.xml                         # Servlet mappings
│   ├── META-INF/
│   ├── css/                                # Stylesheets
│   ├── images/                             # Assets & screenshots
│   ├── welcome.html                        # Landing page
│   ├── login.html
│   ├── register.html
│   ├── dashboard.html                      # Admin dashboard
│   ├── userdashboard.html                  # Member dashboard
│   ├── bookadd.html
│   ├── booklist.html
│   ├── memberadd.html
│   ├── memberlist.html
│   ├── issuebook.html
│   ├── issuedbooklist.html
│   └── script.js                           # AJAX & UI logic
│
├── build.xml                               # Apache Ant build config
├── nbproject/                              # NetBeans project metadata
├── dist/                                   # Built artifacts (.war)
├── .gitignore
├── LICENSE
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

- JDK 11 or higher
- Apache Tomcat 10+
- MySQL 8+
- NetBeans IDE, IntelliJ IDEA, or Eclipse

### Installation

```bash
# 1. Clone the repository
git clone https://github.com/roshanG077/libraryManagementSystem.git

# 2. Open the project in your IDE
#    NetBeans: File → Open Project → select LibraryManagementSystem

# 3. Configure Apache Tomcat in your IDE

# 4. Set up the database (see below)

# 5. Update database credentials (see Configuration)

# 6. Build and run the project via your IDE or Ant
```

### Database Setup

1. Open **MySQL Workbench** or any MySQL client
2. Create a new database:
   ```sql
   CREATE DATABASE library_management;
   ```
3. The application auto-initializes tables on first run via `InitDB.java`

---

## ⚙️ Configuration

Edit `src/java/com/finlogic/config/DBConnection.java` with your local credentials:

```java
private static final String URL  = "jdbc:mysql://localhost:3306/library_management";
private static final String USER = "root";        // Your MySQL username
private static final String PASS = "";            // Your MySQL password
```

> **Important:** Keep `DBConnection.java` excluded from version control if it contains real credentials. Add it to `.gitignore` or use environment variables in production.

---

## 🚢 Deployment

This project is designed for **Apache Tomcat 10+** with Java 11+.

### Local Deployment (NetBeans / IDE)

1. Add Tomcat server in IDE settings
2. Clean and build the project — this generates `dist/LibraryManagementSystem.war`
3. Deploy via IDE run button or manually copy `.war` to Tomcat's `webapps/` directory
4. Access via browser at `http://localhost:8080/LibraryManagementSystem/welcome.html`

### Production Deployment

- Use a dedicated **Apache Tomcat** or **Jetty** instance with Java 11+
- Point deployment to the generated `.war` file
- Set database credentials via environment variables or an external config file (never commit credentials)
- Disable stack traces in production by configuring Tomcat's `error-page` in `web.xml`
- Ensure the MySQL user has least-privilege access to `library_management`

---

## 🔐 Security

- Session-based authentication for Admin and Member roles
- Password hashing before database storage
- Role-based access control — each panel validates session role on every request
- AJAX endpoints protected — unauthorized requests are rejected server-side
- Secure logout — session invalidation and redirect
- Servlet URL mappings restrict direct HTML access to sensitive pages

---

## 👥 User Roles

| Role    | Login URL                                         | Access                                    |
|---------|---------------------------------------------------|-------------------------------------------|
| Admin   | `/LibraryManagementSystem/login.html`             | Full system access — books, members, settings |
| Member  | `/LibraryManagementSystem/login.html`             | Personal dashboard, issued books, fines   |

> Role is detected server-side post-login and redirects to the appropriate dashboard automatically.

---

## ⚠️ Known Limitations

- No email notifications for due dates or overdue alerts
- No real-time push updates — page refresh required for live data changes
- Fine calculation is rule-based (flat daily rate) — no grace period or tiered penalties
- No bulk book import (CSV / Excel)
- No barcode or QR code scanning support
- Optimized for local Tomcat deployment; not production-hardened out of the box
- No automated test suite
- No horizontal scaling or caching layer

---

## 📈 Future Enhancements

- [ ] Email notifications for overdue books and return reminders
- [ ] Barcode / QR code scanning for book check-in and check-out
- [ ] Advanced search and filter for book catalogue
- [ ] Bulk book import via CSV upload
- [ ] Analytics dashboard with charts (issue trends, popular books)
- [ ] REST API layer for mobile app support
- [ ] Docker containerization for easy deployment
- [ ] Tiered fine calculation with grace period support
- [ ] Member self-service book renewal

---

## 🤝 Contributing

Contributions, issues, and feature requests are welcome.

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/your-feature`)
3. Commit your changes (`git commit -m 'Add your feature'`)
4. Push to the branch (`git push origin feature/your-feature`)
5. Open a Pull Request

---

## 🧪 Testing

The system has been manually tested across all major workflows including authentication, book issuance, return processing, fine calculation, and role-based access control. Edge cases such as invalid login attempts, duplicate registrations, returning already-returned books, and unauthorized access to admin routes were also verified.

| Module                     | Test Coverage       |
|----------------------------|---------------------|
| Authentication (all roles) | Manual              |
| Book CRUD operations       | Manual              |
| Member CRUD operations     | Manual              |
| Book issue flow            | Manual              |
| Return & fine calculation  | Manual              |
| Role-based access control  | Manual              |
| Overdue detection          | Manual              |

No automated test suite is currently implemented.

---

## 📸 Screenshots

### Admin Dashboard
![Admin Dashboard](https://raw.githubusercontent.com/roshanG077/libraryManagementSystem/main/LibraryManagementSystem/web/images/screenshorts/admin-dashboard.png)

### Book Management
![Book Management](https://raw.githubusercontent.com/roshanG077/libraryManagementSystem/main/LibraryManagementSystem/web/images/screenshorts/book-management.png)

### Member Management
![Member Management](https://raw.githubusercontent.com/roshanG077/libraryManagementSystem/main/LibraryManagementSystem/web/images/screenshorts/member-management.png)

### Issue Book
![Issue Book](https://raw.githubusercontent.com/roshanG077/libraryManagementSystem/main/LibraryManagementSystem/web/images/screenshorts/issue-book.png)

### Return Book
![Return Book](https://raw.githubusercontent.com/roshanG077/libraryManagementSystem/main/LibraryManagementSystem/web/images/screenshorts/return-book.png)

### Overdue & Fines
![Overdue and Fines](https://raw.githubusercontent.com/roshanG077/libraryManagementSystem/main/LibraryManagementSystem/web/images/screenshorts/overdue-fines.png)

### Member Dashboard
![Member Dashboard](https://raw.githubusercontent.com/roshanG077/libraryManagementSystem/main/LibraryManagementSystem/web/images/screenshorts/member-dashboard.png)

### System Settings
![System Settings](https://raw.githubusercontent.com/roshanG077/libraryManagementSystem/main/LibraryManagementSystem/web/images/screenshorts/system-settings.png)

---

## 🌐 Live Demo

Live demo not deployed yet — local environment only.

To run locally:
```
http://localhost:8080/LibraryManagementSystem/welcome.html
```

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<p align="center">
  Designed &amp; Developed by <strong>Roshan Gupta</strong>
</p>