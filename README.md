# LibraryOS

LibraryOS is a Java-based Library Management System designed to manage books, members, issue/return workflows, and administrative operations.

The application is built using Java Servlets, JDBC, MySQL, and AJAX with a responsive frontend and role-based access control.

---

## Features

### Authentication & Authorization
- Session-based authentication
- Role-based access control for Admin and Members
- Protected routes and dashboard separation

### Admin Functions
- Manage books and inventory
- Add, update, and remove members
- Issue and return books
- Track overdue books and fines
- Configure system settings

### Member Functions
- Browse available books
- View issued books and return history
- Check overdue status and fines
- Access personal dashboard

### System Features
- Real-time dashboard statistics
- Dynamic fine calculation
- AJAX-based interactions without full page reloads
- Responsive UI for different screen sizes

---

## Screenshots

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

## Tech Stack

| Layer      | Technology |
|------------|------------|
| Backend    | Java Servlets (Jakarta EE) |
| Database   | MySQL |
| Frontend   | HTML5, CSS3, Vanilla JavaScript |
| Server     | Apache Tomcat |
| Build Tool | Apache Ant |

---

## Project Structure

```text
LibraryManagementSystem/
├── src/java/com/finlogic/
│   ├── config/
│   ├── dao/
│   ├── model/
│   └── servlet/
│
└── web/
    ├── WEB-INF/
    ├── css/
    ├── images/
    ├── script.js
    ├── login.html
    ├── dashboard.html
    └── userdashboard.html
```

---

## Database Configuration

Create a MySQL database named:

```sql
library_management
```

Update database credentials in:

```text
src/java/com/finlogic/config/DBConnection.java
```

Example:

```java
private static final String URL = "jdbc:mysql://localhost:3306/library_management";
private static final String USER = "your_username";
private static final String PASSWORD = "your_password";
```

---

## Setup & Installation

### Prerequisites
- JDK 11 or higher
- Apache Tomcat 10+
- MySQL 8+
- NetBeans, IntelliJ IDEA, or Eclipse

### Run the Project

1. Clone the repository
2. Import the project into your IDE
3. Configure Apache Tomcat
4. Create the MySQL database
5. Update database credentials
6. Build and run the project

Open in browser:

```text
http://localhost:8080/LibraryManagementSystem/welcome.html
```

## License

This project is licensed under the MIT License.