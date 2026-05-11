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
import java.sql.Date;

public class IssuedBookInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isBlank()) {
            response.sendRedirect("IssuedBook");
            return;
        }

        int issuedId;
        try { issuedId = Integer.parseInt(idParam.trim()); }
        catch (NumberFormatException e) { response.sendRedirect("IssuedBook"); return; }

        IssueBook ib = IssueBookDAO.getIssueBookById(issuedId);
        if (ib == null) { response.sendRedirect("IssuedBook?error=not_found"); return; }

        Book   book   = BookDAO.getBookById(ib.getBookId());
        Member member = MemberDAO.getMemberById(ib.getMemberId());
        Date   today  = new Date(System.currentTimeMillis());

        double penalty    = ib.isReturned() ? ib.getPenaltyAmount() : IssueBookDAO.calculatePenalty(ib, today);
        int    daysOverdue = ib.isReturned() ? 0 : IssueBookDAO.calculateDaysOverdue(ib, today);

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Issue Details - Library Management System</title>");
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

        // Main
        out.println("<div class='main-content'>");
        out.println("<header class='top-header'>");
        out.println("<div class='welcome'><h1>Issue Record #" + ib.getId() + "</h1><p>Full details for this issue record</p></div>");
        out.println("<div class='user-profile'>");
        out.println("<button id='theme-toggle' class='theme-toggle'><i class='fas fa-moon'></i> Dark Mode</button>");
        out.println("<div class='user-avatar'>L</div><span class='user-name'>Admin</span>");
        out.println("</div></header>");

        // Info card
        out.println("<div class='info-card'>");
        out.println("<div class='card-header'><h3><i class='fas fa-info-circle'></i> Issue Details</h3></div>");
        out.println("<table class='info-table'>");
        out.println("<tr><th>Book</th><td>" + (book != null ? esc(book.getTitle()) + " by " + esc(book.getAuthor()) : "ID: " + ib.getBookId()) + "</td></tr>");
        out.println("<tr><th>Member</th><td>" + (member != null ? esc(member.getName()) + " — " + esc(member.getEmail()) : "ID: " + ib.getMemberId()) + "</td></tr>");
        out.println("<tr><th>Issue Date</th><td>" + ib.getIssueDate() + "</td></tr>");
        out.println("<tr><th>Due Date</th><td>" + ib.getReturnDate() + "</td></tr>");
        if (ib.isReturned() && ib.getActualReturnDate() != null)
            out.println("<tr><th>Returned On</th><td>" + ib.getActualReturnDate() + "</td></tr>");
        if (!ib.isReturned() && daysOverdue > 0)
            out.println("<tr><th>Days Overdue</th><td><span class='badge-danger'>" + daysOverdue + " days</span></td></tr>");
        out.println("<tr><th>Status</th><td><span class='status-badge " + (ib.isReturned() ? "returned" : "pending") + "'>"
                    + (ib.isReturned() ? "Returned" : "Not Returned") + "</span></td></tr>");
        out.println("<tr><th>Penalty</th><td><strong>₹" + String.format("%.2f", penalty) + "</strong></td></tr>");
        out.println("<tr><th>Penalty Paid</th><td><span class='status-badge " + (ib.isPenaltyPaid() ? "paid" : "unpaid") + "'>"
                    + (ib.isPenaltyPaid() ? "Yes" : "No") + "</span></td></tr>");
        out.println("</table>");
        out.println("</div>"); // info-card

        // Actions
        out.println("<div class='action-buttons'>");
        out.println("<a href='IssuedBook' class='btn-cancel'><i class='fas fa-arrow-left'></i> Back to List</a>");
        if (!ib.isReturned()) {
            out.println("<a href='ReturnBookServlet?id=" + ib.getId() + "' class='btn-submit'><i class='fas fa-undo'></i> Return This Book</a>");
        }
        out.println("</div>");

        out.println("</div>"); // main-content
        out.println("<script src='script.js'></script></body></html>");
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;").replace("\"","&quot;");
    }
}