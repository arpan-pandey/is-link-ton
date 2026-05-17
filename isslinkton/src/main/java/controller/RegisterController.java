package com.islinkton.controller;

import com.islinkton.model.UserModel;
import com.islinkton.service.RegisterService;
import com.islinkton.utils.PasswordUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserModel user = new UserModel();

        user.setName(request.getParameter("name"));
        user.setEmail(request.getParameter("email"));
        user.setPassword(request.getParameter("password"));

        RegisterService service = new RegisterService();

        if (service.registerUser(user)) {
            response.sendRedirect("login");
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
    }
}