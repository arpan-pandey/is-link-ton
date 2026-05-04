package com.islinkton.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.islinkton.dao.UserDAO;
import com.islinkton.model.UserModel;

@WebServlet(urlPatterns = "/updateUser")
public class UpdateUserServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userID = Integer.parseInt(request.getParameter("userID"));
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String role = request.getParameter("role");

        UserModel currentUser = (UserModel) request.getSession().getAttribute("user");

        // preventing self role change
        if (currentUser != null && currentUser.getId() == userID) {
            if (!currentUser.getRole().equals(role)) {
                response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=You cannot change your own role!");
                return;
            }
        }
        
        UserModel user = new UserModel();
        user.setId(userID);
        user.setName(name);
        user.setEmail(email);
        user.setRole(role);

        try {
            UserDAO dao = new UserDAO();
            dao.updateUser(user);
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?success=User updated");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/admin/dashboard?error=Update failed");
        }
    }
}