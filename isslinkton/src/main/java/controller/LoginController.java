package com.islinkton.controller;

import com.islinkton.model.UserModel;
import com.islinkton.service.LoginService;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        LoginService service = new LoginService();
        UserModel user = service.login(email, password);

        if (user == null) {
            request.setAttribute("error", "Invalid credentials");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            return;
        }

        if (!user.isApproved()) {
            request.setAttribute("error", "Account not approved");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            return;
        }

        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        if ("admin".equals(user.getRole())) {
            response.sendRedirect("admin/dashboard");
        } else {
            response.sendRedirect("student/dashboard");
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
    }
}