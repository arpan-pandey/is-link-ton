package com.islinkton.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.islinkton.dao.UserDAO;
import com.islinkton.model.UserModel;
import com.islinkton.utils.PasswordUtil;

@WebServlet(urlPatterns = { "/addUser" })
public class AddUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        UserModel user = new UserModel();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(PasswordUtil.hashPassword(password)); // hash password
        user.setRole(role);

        try {
            UserDAO dao = new UserDAO();
            dao.insertUser(user);
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?success=User added successfully");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=Failed to add user");
        }
    }
}