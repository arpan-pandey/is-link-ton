package com.islinkton.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.islinkton.dao.UserDAO;
import com.islinkton.model.UserModel;

@WebServlet(urlPatterns = "/deleteUser")
public class DeleteUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        
        UserModel currentUser = (UserModel) request.getSession().getAttribute("user");

        // preventing self-deletion
        if (currentUser != null && currentUser.getEmail().equals(email)) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=You cannot delete yourself!");
            return;
        }

        UserModel user = new UserModel();
        user.setEmail(email);

        try {
            UserDAO dao = new UserDAO();
            dao.deleteUser(user);
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?success=User deleted");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=Delete failed");
        }
    }
}