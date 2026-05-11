package com.finlogic.servlet;

import com.finlogic.dao.MemberDAO;
import com.finlogic.model.Member;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class EditMemberServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String idParam = request.getParameter("id");
        if (idParam == null || idParam.isBlank()) { response.sendRedirect("viewmember"); return; }

        int id;
        try { id = Integer.parseInt(idParam.trim()); }
        catch (NumberFormatException e) { response.sendRedirect("viewmember?error=invalid_id"); return; }

        Member m = MemberDAO.getMemberById(id);
        if (m == null) { response.sendRedirect("viewmember?error=member_not_found"); return; }

        out.println("<!DOCTYPE html><html lang='en'><head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        out.println("<title>Edit Member - Library Management System</title>");
        out.println("<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css'>");
        out.println("<link rel='stylesheet' href='css/updatemember.css'>");
        out.println("</head><body class='updatemember-page'>");

        out.println("<div class='updatemember-container'>");
        out.println("<div class='updatemember-header'>");
        out.println("<i class='fas fa-user-edit'></i>");
        out.println("<h1>Edit Member</h1>");
        out.println("<p>Update details for <strong>" + esc(m.getName()) + "</strong></p>");
        out.println("</div>");

        out.println("<form action='updatemember' method='post' class='updatemember-form'>");
        out.println("<input type='hidden' name='id' value='" + m.getId() + "'>");

        out.println("<label for='name'><i class='fas fa-user'></i> Full Name</label>");
        out.println("<input type='text' id='name' name='name' value='" + esc(m.getName()) + "' required>");

        out.println("<label for='email'><i class='fas fa-envelope'></i> Email Address</label>");
        out.println("<input type='email' id='email' name='email' value='" + esc(m.getEmail()) + "' required>");

        out.println("<label for='phone'><i class='fas fa-phone'></i> Phone Number</label>");
        out.println("<input type='tel' id='phone' name='phone' value='" + m.getPhone() + "' required>");

        out.println("<input type='submit' value='  Update Member'>");
        out.println("<a href='viewmember' class='updatemember-cancel'>  Cancel</a>");
        out.println("</form></div>");
        out.println("</body></html>");
    }

    private String esc(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;")
                .replace("\"","&quot;").replace("'","&#39;");
    }
}