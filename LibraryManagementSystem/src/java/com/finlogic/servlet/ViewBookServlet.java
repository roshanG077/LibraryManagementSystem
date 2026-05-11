package com.finlogic.servlet;

import com.finlogic.dao.BookDAO;
import com.finlogic.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ViewBookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        List<Book> list = BookDAO.getAllBooks();

        // Check for success/error query param
        String success = request.getParameter("success");
        String error   = request.getParameter("error");

        out.println("<!DOCTYPE html>");
        out.println("<html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Books - Library Management System</title>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css'>");
        out.println("<link rel='stylesheet' href='css/style.css'>");
        out.println("</head><body>");

        // Sidebar
        printSidebar(out, "books");

        out.println("<div class='main-content'>");

        // Header
        out.println("<header class='top-header'>");
        out.println("<div class='welcome'><h1>Book Catalogue</h1><p>All books in the library collection</p></div>");
        out.println("<div class='user-profile'>");
        out.println("<button id='theme-toggle' class='theme-toggle'><i class='fas fa-moon'></i> Dark Mode</button>");
        out.println("<div class='user-avatar'>L</div><span class='user-name'>Admin</span>");
        out.println("</div></header>");

        // Toast messages
        if ("added".equals(success))   out.println("<script>document.addEventListener('DOMContentLoaded',()=>showToast('✅ Book added successfully!'));</script>");
        if ("updated".equals(success)) out.println("<script>document.addEventListener('DOMContentLoaded',()=>showToast('✅ Book updated successfully!'));</script>");

        // Quick actions
        out.println("<div class='quick-actions'>");
        out.println("<div class='action-buttons'>");
        out.println("<a href='addform.html' class='quick-btn'><i class='fas fa-plus'></i> Add New Book</a>");
        out.println("<a href='index.html' class='quick-btn'><i class='fas fa-home'></i> Dashboard</a>");
        out.println("</div></div>");

        // Table card
        out.println("<div class='table-card'>");
        out.println("<div class='card-header'>");
        out.println("<h3><i class='fas fa-book'></i> All Books (" + list.size() + ")</h3>");
        out.println("</div>");
        out.println("<div class='table-container'>");
        out.println("<table class='data-table'><thead><tr>");
        out.println("<th>ID</th><th>Title</th><th>Author</th><th>Category</th><th>Stock</th><th>Actions</th>");
        out.println("</tr></thead><tbody>");

        if (list.isEmpty()) {
            out.println("<tr><td colspan='6' class='text-center'><div class='empty-state'><i class='fas fa-book-open'></i><p>No books found. Add your first book!</p></div></td></tr>");
        } else {
            for (Book b : list) {
                String stockBadge = b.getQuantity() < 1 ? "badge-danger" :
                                    b.getQuantity() < 5 ? "badge-warning" : "badge-success";
                out.println("<tr>");
                out.println("<td><strong>BK" + b.getId() + "</strong></td>");
                out.println("<td>" + escHtml(b.getTitle())    + "</td>");
                out.println("<td>" + escHtml(b.getAuthor())   + "</td>");
                out.println("<td>" + escHtml(b.getCategory()) + "</td>");
                out.println("<td><span class='" + stockBadge + "'>" + b.getQuantity() + "</span></td>");
                out.println("<td>");
                out.println("<a href='edit?id=" + b.getId() + "' class='btn-edit'><i class='fas fa-edit'></i> Edit</a>&nbsp;");
                out.println("</td>");
                out.println("</tr>");
            }
        }

        out.println("</tbody></table></div></div>"); // end table-card

        out.println("<div class='action-buttons' style='margin-top:18px;'>");
        out.println("<a href='index.html' class='btn-cancel'><i class='fas fa-arrow-left'></i> Back to Dashboard</a>");
        out.println("</div>");

        out.println("</div>"); // end main-content
        out.println("<script src='script.js'></script></body></html>");
    }

    private void printSidebar(PrintWriter out, String active) {
        out.println("<div class='sidebar'>");
        out.println("<div class='sidebar-header'><h2><i class='fas fa-book-reader'></i> <span>LibSys</span></h2></div>");
        out.println("<ul class='sidebar-menu'>");
        out.println(navItem("index.html",         "fa-home",         "Dashboard",   "dashboard".equals(active)));
        out.println(navItem("addform.html",        "fa-book",         "Books",       "books".equals(active)));
        out.println(navItem("addmember.html",      "fa-users",        "Members",     "members".equals(active)));
        out.println(navItem("issuebook.html",      "fa-exchange-alt", "Issue Book",  "issue".equals(active)));
        out.println(navItem("viewmember",          "fa-list",         "View Members","viewmembers".equals(active)));
        out.println(navItem("IssuedBook",          "fa-clock",        "Issued Books","issued".equals(active)));
        out.println(navItem("ReturnBookServlet",   "fa-undo",         "Return Book", "return".equals(active)));
        out.println(navItem("view",                "fa-book-open",    "View Books",  "books".equals(active)));
        out.println("</ul>");
        out.println("<div class='sidebar-footer'><a href='login.html' class='logout-btn'><i class='fas fa-sign-out-alt'></i> <span class='logout-text'>Logout</span></a></div>");
        out.println("</div>");
    }

    private String navItem(String href, String icon, String label, boolean active) {
        return "<li><a href='" + href + "'" + (active ? " class='active'" : "") +
               "><i class='fas " + icon + "'></i> <span>" + label + "</span></a></li>";
    }

    private String escHtml(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;").replace("\"", "&quot;");
    }
}