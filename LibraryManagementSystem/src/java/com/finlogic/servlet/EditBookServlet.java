package com.finlogic.servlet;

import com.finlogic.dao.BookDAO;
import com.finlogic.model.Book;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EditBookServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isBlank()) {
            response.sendRedirect("view?error=missing_id");
            return;
        }

        int id;
        try { id = Integer.parseInt(idParam.trim()); }
        catch (NumberFormatException e) { response.sendRedirect("view?error=invalid_id"); return; }

        Book b = BookDAO.getBookById(id);
        if (b == null) { response.sendRedirect("view?error=book_not_found"); return; }

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Edit Book - Library Management System</title>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css'>");
        out.println("<link rel='stylesheet' href='css/addbook.css'>");
        out.println("</head><body class='addbook-page'>");

        out.println("<div class='addbook-container'>");
        out.println("<div class='addbook-header'>");
        out.println("<i class='fas fa-edit'></i>");
        out.println("<h1>Edit Book</h1>");
        out.println("<p>Update book details for <strong>" + esc(b.getTitle()) + "</strong></p>");
        out.println("</div>");

        out.println("<form action='update' method='post' class='addbook-form'>");
        out.println("<input type='hidden' name='id' value='" + b.getId() + "'>");

        out.println("<label for='title'><i class='fas fa-book'></i> Book Title</label>");
        out.println("<input type='text' id='title' name='title' value='" + esc(b.getTitle()) + "' required>");

        out.println("<label for='auth'><i class='fas fa-user-pen'></i> Author Name</label>");
        out.println("<input type='text' id='auth' name='auth' value='" + esc(b.getAuthor()) + "' required>");

        out.println("<label for='cate'><i class='fas fa-folder'></i> Category</label>");
        out.println("<input type='text' id='cate' name='cate' value='" + esc(b.getCategory()) + "' required>");

        out.println("<label for='qunt'><i class='fas fa-layer-group'></i> Quantity</label>");
        out.println("<input type='number' id='qunt' name='qunt' value='" + b.getQuantity() + "' min='0' required>");

        out.println("<input type='submit' value='💾  Update Book'>");
        out.println("<a href='view' class='addbook-cancel'>❌  Cancel</a>");
        out.println("</form></div>");
        out.println("</body></html>");
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }
}