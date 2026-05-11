package com.finlogic.servlet;

import com.finlogic.dao.BookDAO;
import com.finlogic.dao.IssueBookDAO;
import com.finlogic.dao.MemberDAO;
import com.finlogic.model.Book;
import com.finlogic.model.IssueBook;
import com.finlogic.model.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class IssuedBookservlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        List<IssueBook> list = IssueBookDAO.getAllIssuedBooks();

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Issued Books - Library Management System</title>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css'>");
        out.println("<link rel='stylesheet' href='css/style.css'>");
        out.println("</head><body>");

        // Sidebar
        out.println("<div class='sidebar'>");
        out.println("<div class='sidebar-header'><h2><i class='fas fa-book-reader'></i> <span>LibSys</span></h2></div>");
        out.println("<ul class='sidebar-menu'>");
        out.println("<li><a href='index.html'><i class='fas fa-home'></i> <span>Dashboard</span></a></li>");
        out.println("<li><a href='addform.html'><i class='fas fa-book'></i> <span>Books</span></a></li>");
        out.println("<li><a href='addmember.html'><i class='fas fa-users'></i> <span>Members</span></a></li>");
        out.println("<li><a href='issuebook.html'><i class='fas fa-exchange-alt'></i> <span>Issue Book</span></a></li>");
        out.println("<li><a href='viewmember'><i class='fas fa-list'></i> <span>View Members</span></a></li>");
        out.println("<li><a href='IssuedBook' class='active'><i class='fas fa-clock'></i> <span>Issued Books</span></a></li>");
        out.println("<li><a href='ReturnBookServlet'><i class='fas fa-undo'></i> <span>Return Book</span></a></li>");
        out.println("<li><a href='view'><i class='fas fa-book-open'></i> <span>View Books</span></a></li>");
        out.println("</ul>");
        out.println("<div class='sidebar-footer'><a href='login.html' class='logout-btn'><i class='fas fa-sign-out-alt'></i> <span class='logout-text'>Logout</span></a></div>");
        out.println("</div>");

        // Main content
        out.println("<div class='main-content'>");
        out.println("<header class='top-header'>");
        out.println("<div class='welcome'><h1>Issued Books</h1><p>All currently and previously issued books</p></div>");
        out.println("<div class='user-profile'>");
        out.println("<button id='theme-toggle' class='theme-toggle'><i class='fas fa-moon'></i> Dark Mode</button>");
        out.println("<div class='user-avatar'>L</div><span class='user-name'>Admin</span>");
        out.println("</div></header>");

        // Quick actions
        out.println("<div class='quick-actions'><div class='action-buttons'>");
        out.println("<a href='issuebook.html' class='quick-btn'><i class='fas fa-book-open'></i> Issue Book</a>");
        out.println("<a href='ReturnBookServlet' class='quick-btn'><i class='fas fa-undo'></i> Return Book</a>");
        out.println("</div></div>");

        // Table
        out.println("<div class='table-card'>");
        out.println("<div class='card-header'><h3><i class='fas fa-clock'></i> All Issue Records (" + list.size() + ")</h3></div>");
        out.println("<div class='table-container'><table class='data-table'><thead><tr>");
        out.println("<th>#</th><th>Book</th><th>Member</th><th>Issue Date</th><th>Due Date</th><th>Status</th><th>Action</th>");
        out.println("</tr></thead><tbody>");

        if (list.isEmpty()) {
            out.println("<tr><td colspan='7' class='text-center'><div class='empty-state'><i class='fas fa-book'></i><p>No issued books found.</p></div></td></tr>");
        } else {
            for (IssueBook ib : list) {
                // Fetch names for display
                Book   book   = BookDAO.getBookById(ib.getBookId());
                Member member = MemberDAO.getMemberById(ib.getMemberId());

                String bookDisplay   = (book   != null) ? esc(book.getTitle())   + " <small>(BK" + ib.getBookId()   + ")</small>" : "ID: " + ib.getBookId();
                String memberDisplay = (member != null) ? esc(member.getName())  + " <small>(#"  + ib.getMemberId() + ")</small>" : "ID: " + ib.getMemberId();

                String statusCls  = ib.isReturned() ? "badge-success" : "badge-warning";
                String statusText = ib.isReturned() ? "Returned"      : "Issued";

                out.println("<tr>");
                out.println("<td>" + ib.getId() + "</td>");
                out.println("<td>" + bookDisplay   + "</td>");
                out.println("<td>" + memberDisplay + "</td>");
                out.println("<td>" + ib.getIssueDate()  + "</td>");
                out.println("<td>" + ib.getReturnDate() + "</td>");
                out.println("<td><span class='" + statusCls + "'>" + statusText + "</span></td>");
                out.println("<td><a href='IssuedBookInfoServlet?id=" + ib.getId() + "' class='btn-info'><i class='fas fa-info-circle'></i> Details</a></td>");
                out.println("</tr>");
            }
        }

        out.println("</tbody></table></div></div>");
        out.println("<div class='action-buttons' style='margin-top:18px;'>");
        out.println("<a href='index.html' class='btn-cancel'><i class='fas fa-arrow-left'></i> Back to Dashboard</a>");
        out.println("</div>");
        out.println("</div>"); // main-content
        out.println("<script src='script.js'></script></body></html>");
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
    }
}