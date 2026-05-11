/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.finlogic.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.finlogic.model.Member;
import com.finlogic.dao.MemberDAO;

/**
 *
 * @author njuser
 */
public class ViewMemberByIdServlet extends HttpServlet {

    

    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
     response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));


        Member m = MemberDAO.getMemberById(id);
        
        out.println("<html><head><title>View Member Detaile </title>");
        out.println("<style>");
        out.println("table{border-collapse:collapse;width:90%;}");
        out.println("th,td{border:1px solid black;padding:8px;text-align:center;}");
        out.println("th{background:#f2f2f2;}");
        out.println("</style></head><body>");
        out.println("<h2>Member Details</h2>");

        if (m != null) {
            out.println("<table>");
        out.println("<tr><th>Memeber ID</th><th>Member Name</th><th>Email</th><th>Contact No. </th><th>Action</th></tr>");

            out.println("<tr>");
            out.println("<td>" + m.getId() + "</td>");
            out.println("<td>" + m.getName() + "</td>");
            out.println("<td>" + m.getEmail()+ "</td>");
            out.println("<td>" + m.getPhone()+ "</td>");
            out.println("<td>" + m.getPhone()+ "</td>");
            out.println("</tr></table>");
            out.println("</br></br>");
            out.println("<b><a href='IssuedBook'>Back</a></b>");
            out.println("</body></html>");
           
       
            
        } else {
            out.println("<p>Member not found</p>");
        }
         
    }
}

    

