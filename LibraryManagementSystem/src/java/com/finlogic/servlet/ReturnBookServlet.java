package com.finlogic.servlet;

import com.finlogic.dao.IssueBookDAO;
import com.finlogic.model.IssueBook;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

public class ReturnBookServlet extends HttpServlet {

    // ── GET: Show find-form OR confirmation page ──────────────────────────────
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.isBlank()) {
            showConfirmPage(request, response, idParam.trim());
        } else {
            showFindForm(response);
        }
    }

    // ── POST: Search by bookId + memberId and redirect to confirm page ────────
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String bookIdParam   = request.getParameter("bookId");
        String memberIdParam = request.getParameter("memberId");

        if (bookIdParam == null || memberIdParam == null ||
            bookIdParam.isBlank() || memberIdParam.isBlank()) {
            response.sendRedirect("ReturnBookServlet?error=missing_fields");
            return;
        }

        try {
            int bookId   = Integer.parseInt(bookIdParam.trim());
            int memberId = Integer.parseInt(memberIdParam.trim());

            IssueBook ib = IssueBookDAO.getIssuedBook(bookId, memberId);
            if (ib == null) {
                sendErrorJs(response, "No active issue record found for Book ID " + bookId + " and Member ID " + memberId + ".", "ReturnBookServlet");
                return;
            }
            // Redirect to confirm page with the issue id
            response.sendRedirect("ReturnBookServlet?id=" + ib.getId());

        } catch (NumberFormatException e) {
            response.sendRedirect("ReturnBookServlet?error=invalid_input");
        }
    }

    // ── FIND FORM ─────────────────────────────────────────────────────────────
    private void showFindForm(HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Return Book - Library Management System</title>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css'>");
        out.println("<link rel='stylesheet' href='css/style.css'>");
        out.println("</head><body>");
        printSidebar(out, "return");

        out.println("<div class='main-content'>");
        out.println("<header class='top-header'>");
        out.println("<div class='welcome'><h1>Return Book</h1><p>Find the issued book record to process a return</p></div>");
        out.println("<div class='user-profile'>");
        out.println("<button id='theme-toggle' class='theme-toggle'><i class='fas fa-moon'></i> Dark Mode</button>");
        out.println("<div class='user-avatar'>L</div><span class='user-name'>Admin</span>");
        out.println("</div></header>");

        out.println("<div style='max-width:520px;margin:0 auto;'><div class='dashboard-card'>");
        out.println("<div class='card-header'><h3><i class='fas fa-search'></i> Find Issued Book</h3></div>");
        out.println("<form action='ReturnBookServlet' method='post'>");
        out.println("<div class='form-group'><label for='bookId'><i class='fas fa-book'></i> Book ID</label>");
        out.println("<input type='number' id='bookId' name='bookId' placeholder='Enter Book ID' min='1' required></div>");
        out.println("<div class='form-group'><label for='memberId'><i class='fas fa-user'></i> Member ID</label>");
        out.println("<input type='number' id='memberId' name='memberId' placeholder='Enter Member ID' min='1' required></div>");
        out.println("<div class='form-actions'>");
        out.println("<button type='submit' class='btn-submit'><i class='fas fa-search'></i> Find Record</button>");
        out.println("<a href='IssuedBook' class='btn-cancel'><i class='fas fa-list'></i> All Issued Books</a>");
        out.println("</div></form></div></div>");
        out.println("</div>"); // main-content
        out.println("<script src='script.js'></script></body></html>");
    }

    // ── CONFIRM PAGE ──────────────────────────────────────────────────────────
    private void showConfirmPage(HttpServletRequest request, HttpServletResponse response, String idParam)
            throws IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        int issueId;
        try { issueId = Integer.parseInt(idParam); }
        catch (NumberFormatException e) { response.sendRedirect("IssuedBook"); return; }

        IssueBook ib = IssueBookDAO.getIssueBookById(issueId);
        if (ib == null) {
            sendErrorJs(response, "Issue record #" + issueId + " not found.", "IssuedBook");
            return;
        }
        if (ib.isReturned()) {
            sendErrorJs(response, "This book has already been returned!", "IssuedBook");
            return;
        }

        Date   today      = new Date(System.currentTimeMillis());
        double penalty    = IssueBookDAO.calculatePenalty(ib, today);
        int    daysOverdue = IssueBookDAO.calculateDaysOverdue(ib, today);

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Confirm Return - Library Management System</title>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css'>");
        out.println("<link rel='stylesheet' href='css/style.css'>");
        out.println("</head><body>");
        printSidebar(out, "return");

        out.println("<div class='main-content'>");
        out.println("<header class='top-header'>");
        out.println("<div class='welcome'><h1>Confirm Return</h1><p>Review details before processing the return</p></div>");
        out.println("<div class='user-profile'>");
        out.println("<button id='theme-toggle' class='theme-toggle'><i class='fas fa-moon'></i> Dark Mode</button>");
        out.println("<div class='user-avatar'>L</div><span class='user-name'>Admin</span>");
        out.println("</div></header>");

        // Issue summary
        out.println("<div class='info-card'>");
        out.println("<div class='card-header'><h3><i class='fas fa-info-circle'></i> Return Summary</h3></div>");
        out.println("<table class='info-table'>");
        out.println("<tr><th>Issue ID</th><td>#" + ib.getId() + "</td></tr>");
        out.println("<tr><th>Book ID</th><td>" + ib.getBookId() + "</td></tr>");
        out.println("<tr><th>Member ID</th><td>" + ib.getMemberId() + "</td></tr>");
        out.println("<tr><th>Issue Date</th><td>" + ib.getIssueDate() + "</td></tr>");
        out.println("<tr><th>Due Date</th><td>" + ib.getReturnDate() + "</td></tr>");
        out.println("<tr><th>Return Date (Today)</th><td>" + today + "</td></tr>");
        out.println("<tr><th>Days Overdue</th><td>" +
                    (daysOverdue > 0 ? "<span class='badge-danger'>" + daysOverdue + " days</span>" : "<span class='badge-success'>On time</span>") +
                    "</td></tr>");
        out.println("<tr><th>Penalty</th><td><strong>₹" + String.format("%.2f", penalty) + "</strong></td></tr>");
        out.println("</table></div>");

        // Confirm form
        out.println("<div class='dashboard-card'>");
        out.println("<form action='ConfirmReturnServlet' method='post'>");
        out.println("<input type='hidden' name='issueId' value='" + ib.getId()       + "'>");
        out.println("<input type='hidden' name='penalty' value='" + penalty          + "'>");
        out.println("<input type='hidden' name='bookId'  value='" + ib.getBookId()   + "'>");
        out.println("<input type='hidden' name='memberId' value='" + ib.getMemberId() + "'>");
        out.println("<div class='form-group'>");
        out.println("<label for='penaltyPaid'><i class='fas fa-money-bill-wave'></i> Penalty Paid?</label>");
        out.println("<select id='penaltyPaid' name='penaltyPaid'>");
        out.println("<option value='false'>No — will record as unpaid</option>");
        out.println("<option value='true'>Yes — penalty collected</option>");
        out.println("</select>");
        if (penalty == 0) out.println("<small>No penalty applicable for this return.</small>");
        else out.println("<small>₹10 per overdue day × " + daysOverdue + " days = ₹" + String.format("%.2f", penalty) + "</small>");
        out.println("</div>");
        out.println("<div class='form-actions'>");
        out.println("<button type='submit' class='btn-submit'><i class='fas fa-check'></i> Confirm Return</button>");
        out.println("<a href='IssuedBookInfoServlet?id=" + ib.getId() + "' class='btn-cancel'><i class='fas fa-times'></i> Cancel</a>");
        out.println("</div></form></div>");

        out.println("</div>"); // main-content
        out.println("<script src='script.js'></script></body></html>");
    }

    // ── SIDEBAR HELPER ────────────────────────────────────────────────────────
    private void printSidebar(PrintWriter out, String active) {
        out.println("<div class='sidebar'>");
        out.println("<div class='sidebar-header'><h2><i class='fas fa-book-reader'></i> <span>LibSys</span></h2></div>");
        out.println("<ul class='sidebar-menu'>");
        out.println("<li><a href='index.html'" + a("dashboard", active) + "><i class='fas fa-home'></i> <span>Dashboard</span></a></li>");
        out.println("<li><a href='addform.html'" + a("books", active) + "><i class='fas fa-book'></i> <span>Books</span></a></li>");
        out.println("<li><a href='addmember.html'" + a("members", active) + "><i class='fas fa-users'></i> <span>Members</span></a></li>");
        out.println("<li><a href='issuebook.html'" + a("issue", active) + "><i class='fas fa-exchange-alt'></i> <span>Issue Book</span></a></li>");
        out.println("<li><a href='viewmember'" + a("viewmembers", active) + "><i class='fas fa-list'></i> <span>View Members</span></a></li>");
        out.println("<li><a href='IssuedBook'" + a("issued", active) + "><i class='fas fa-clock'></i> <span>Issued Books</span></a></li>");
        out.println("<li><a href='ReturnBookServlet'" + a("return", active) + "><i class='fas fa-undo'></i> <span>Return Book</span></a></li>");
        out.println("<li><a href='view'" + a("viewbooks", active) + "><i class='fas fa-book-open'></i> <span>View Books</span></a></li>");
        out.println("</ul>");
        out.println("<div class='sidebar-footer'><a href='login.html' class='logout-btn'><i class='fas fa-sign-out-alt'></i> <span class='logout-text'>Logout</span></a></div>");
        out.println("</div>");
    }

    private String a(String key, String active) {
        return key.equals(active) ? " class='active'" : "";
    }

    private void sendErrorJs(HttpServletResponse response, String msg, String redirectUrl) throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>alert('" + msg.replace("'", "\\'") + "'); window.location='" + redirectUrl + "';</script>");
    }
}