package com.finlogic.servlet;

import com.finlogic.dao.BookDAO;
import com.finlogic.dao.IssueBookDAO;
import com.finlogic.model.Book;
import com.finlogic.model.IssueBook;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

public class ConfirmReturnServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String issueIdParam    = request.getParameter("issueId");
        String penaltyParam    = request.getParameter("penalty");
        String penaltyPaidParam = request.getParameter("penaltyPaid");
        String bookIdParam     = request.getParameter("bookId");
        String memberIdParam   = request.getParameter("memberId");

        // Validate
        if (issueIdParam == null || penaltyParam == null || penaltyPaidParam == null ||
            bookIdParam == null  || memberIdParam == null) {
            out.println("<script>alert('Invalid submission data.'); window.location='ReturnBookServlet';</script>");
            return;
        }

        int    issueId;
        double penalty;
        boolean penaltyPaid;
        int bookId, memberId;
        try {
            issueId     = Integer.parseInt(issueIdParam.trim());
            penalty     = Double.parseDouble(penaltyParam.trim());
            penaltyPaid = Boolean.parseBoolean(penaltyPaidParam.trim());
            bookId      = Integer.parseInt(bookIdParam.trim());
            memberId    = Integer.parseInt(memberIdParam.trim());
        } catch (NumberFormatException e) {
            out.println("<script>alert('Invalid input values.'); window.location='ReturnBookServlet';</script>");
            return;
        }

        IssueBook ib = IssueBookDAO.getIssueBookById(issueId);
        if (ib == null) {
            out.println("<script>alert('Issue record not found.'); window.location='IssuedBook';</script>");
            return;
        }
        if (ib.isReturned()) {
            out.println("<script>alert('This book has already been returned!'); window.location='IssuedBook';</script>");
            return;
        }

        // Mark as returned
        ib.setReturned(true);
        ib.setActualReturnDate(new Date(System.currentTimeMillis()));
        ib.setPenaltyAmount(penalty);
        ib.setPenaltyPaid(penaltyPaid);

        int result = IssueBookDAO.updateIssueBook(ib);
        if (result > 0) {
            // Restore book stock
            Book book = BookDAO.getBookById(bookId);
            if (book != null) {
                book.setQuantity(book.getQuantity() + 1);
                BookDAO.updateBook(book);
            }

            // Success page
            out.println("<!DOCTYPE html><html lang='en'><head>");
            out.println("<meta charset='UTF-8'><meta name='viewport' content='width=device-width, initial-scale=1.0'>");
            out.println("<title>Return Successful - Library Management System</title>");
            out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css'>");
            out.println("<link rel='stylesheet' href='css/style.css'>");
            out.println("<style>");
            out.println(".success-page{display:flex;justify-content:center;align-items:center;min-height:100vh;background:var(--bg);}");
            out.println(".success-card{background:var(--card-bg);border:1px solid var(--border);border-radius:16px;padding:50px 44px;max-width:480px;width:100%;text-align:center;box-shadow:var(--shadow-lg);}");
            out.println(".success-icon{font-size:72px;color:#10b981;margin-bottom:22px;}");
            out.println(".success-card h2{font-size:26px;color:var(--text);margin-bottom:10px;}");
            out.println(".success-card p{color:var(--text-muted);font-size:15px;margin:5px 0;}");
            out.println(".success-actions{display:flex;gap:12px;margin-top:30px;justify-content:center;}");
            out.println("</style>");
            out.println("</head><body>");
            out.println("<div class='success-page'><div class='success-card'>");
            out.println("<div class='success-icon'><i class='fas fa-check-circle'></i></div>");
            out.println("<h2>Book Returned Successfully!</h2>");
            out.println("<p>Issue #" + issueId + " has been processed.</p>");
            out.println("<p>Penalty: <strong>₹" + String.format("%.2f", penalty) + "</strong></p>");
            out.println("<p>Penalty Paid: <strong>" + (penaltyPaid ? "Yes ✅" : "No — recorded as unpaid") + "</strong></p>");
            out.println("<div class='success-actions'>");
            out.println("<a href='IssuedBook' class='btn-cancel'><i class='fas fa-list'></i> All Issues</a>");
            out.println("<a href='ReturnBookServlet' class='btn-submit'><i class='fas fa-undo'></i> Return Another</a>");
            out.println("</div></div></div>");
            out.println("<script src='script.js'></script></body></html>");
        } else {
            out.println("<script>alert('Failed to process return. Please try again.'); window.location='ReturnBookServlet';</script>");
        }
    }
}