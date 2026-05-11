package com.finlogic.servlet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

import com.finlogic.model.Book;
import com.finlogic.dao.BookDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author njuser
 */
public class ViewBookByIdServlet extends HttpServlet {

    

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));

        Book b = BookDAO.getBookById(id);

        out.println("<html><head><title>View Books</title>");
        out.println("<style>");
        out.println("table{border-collapse:collapse;width:90%;}");
        out.println("th,td{border:1px solid black;padding:8px;text-align:center;}");
        out.println("th{background:#f2f2f2;}");
        out.println("</style></head><body>");

        out.println("<h2> Book List</h2>");
        out.println("<table>");
        out.println("<tr><th>ID</th><th>Title</th><th>Author</th><th>Category</th><th>Quantity</th><th>Action</th></tr>");

        if (b != null) {
                  out.println("<tr>");
            out.println("<td>" + b.getId() + "</td>");
            out.println("<td>" + b.getTitle() + "</td>");
            out.println("<td>" + b.getAuthor()+ "</td>");
            out.println("<td>" + b.getCategory()+ "</td>");
            out.println("<td>" + b.getQuantity()+ "</td>");
            out.println("<td>");
            out.println("<a href='edit?id=" + b.getId() + "'>Edit</a>");
            out.println("</td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("<br><a href='index.html'> Back</a>");
            out.println("</body></html>");
    
        } else {
            out.println("<p>Book not found</p>");
        }

        out.println("<a href='issueview'>Back</a>");
        out.println("</body></html>");
    }
}
