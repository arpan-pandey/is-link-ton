package com.islinkton.controller;

import com.islinkton.dao.UserDAO;
import com.islinkton.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminController extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        // Security Check
        if (currentUser == null || !"admin".equalsIgnoreCase(currentUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // get pending users for approval
            List<User> pendingUsers = userDAO.getPendingUsers();
            List<User> approvedUsers = userDAO.getAllApprovedUsers();
            
            request.setAttribute("pendingUsers", pendingUsers);
            request.setAttribute("approvedUsers", approvedUsers);

            request.getRequestDispatcher("/pages/admin/admin-dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading dashboard: " + e.getMessage());
            request.getRequestDispatcher("/pages/admin/admin-dashboard.jsp").forward(request, response);
        }
    }

    // handle approval / rejection
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        String userIdStr = request.getParameter("userId");

        if (userIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        int userId = Integer.parseInt(userIdStr);

        try {
            if ("approve".equals(action)) {
                userDAO.approveUser(userId);
                request.setAttribute("message", "User approved successfully!");
            } else if ("reject".equals(action)) {
                // Optional: delete or mark as rejected
                userDAO.deleteUser(userId); // You can implement this
                request.setAttribute("message", "User rejected.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Action failed: " + e.getMessage());
        }

        // Refresh the page
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
}