package com.islinkton.controller;

import com.islinkton.dao.UserDAO;
import com.islinkton.dao.PetitionDAO;
import com.islinkton.dao.ThreadDAO;
import com.islinkton.model.User;
import com.islinkton.model.Petition;
import com.islinkton.model.Thread;
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
    private PetitionDAO petitionDAO = new PetitionDAO();
    private ThreadDAO threadDAO = new ThreadDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        // 🚀 STRICT SERVER-SIDE BACKEND SECURITY GUARD
        if (currentUser == null || !"Admin".equalsIgnoreCase(currentUser.getRole())) {
            session.setAttribute("error", "Access Denied: You do not possess Administrative Privileges.");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        try {
            // 1. Fetch User Data Streams
            List<User> pendingUsers = userDAO.getPendingUsers();
            List<User> approvedUsers = userDAO.getAllApprovedUsers();
            
            // 2. Fetch Petition Data Streams (Pending verification only)
            List<Petition> pendingPetitions = petitionDAO.getPendingPetitions();
            
            // 3. Fetch System Forum Discussion Threads
            List<Thread> allThreads = threadDAO.getPendingThreads();
            
            // Bind all collections to request context for dashboard table rendering
            request.setAttribute("pendingUsers", pendingUsers);
            request.setAttribute("allUsers", approvedUsers);
            request.setAttribute("pendingPetitions", pendingPetitions);
            request.setAttribute("allThreads", allThreads);

            request.getRequestDispatcher("/pages/admin/admin-dashboard.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading administrative metric data: " + e.getMessage());
            request.getRequestDispatcher("/pages/admin/admin-dashboard.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("user");

        // 🚀 CRITICAL POST INTERCEPTOR SECURITY GUARD
        if (currentUser == null || !"Admin".equalsIgnoreCase(currentUser.getRole())) {
            session.setAttribute("error", "Unauthorized state alteration execution attempt.");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        String targetType = request.getParameter("targetType"); // e.g., "user", "petition", "thread"
        String action = request.getParameter("action");         // e.g., "approve", "reject", "delete"
        String idStr = request.getParameter("id");

        if (idStr == null || action == null || targetType == null) {
            session.setAttribute("error", "Invalid form modification transaction payload.");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        int targetId = Integer.parseInt(idStr);

        try {
            // Processing Router Matrix based on form target types
            switch (targetType.toLowerCase()) {
                case "user":
                    if ("approve".equals(action)) {
                        userDAO.approveUser(targetId);
                        session.setAttribute("message", "User account approved successfully!");
                    } else if ("reject".equals(action) || "delete".equals(action)) {
                        userDAO.deleteUser(targetId);
                        session.setAttribute("message", "User account registration dropped.");
                    }
                    break;

                case "petition":
                    if ("approve".equals(action)) {
                        petitionDAO.approvePetition(targetId); // Update your PetitionDAO to include this method
                        session.setAttribute("message", "Student petition approved and published successfully.");
                    } else if ("reject".equals(action) || "delete".equals(action)) {
                        petitionDAO.deletePetition(targetId, currentUser.getRole());
                        session.setAttribute("message", "Petition submission dropped from verification queue.");
                    }
                    break;

                case "thread":
                    if ("delete".equals(action)) {
                        threadDAO.deleteThread(targetId, currentUser.getId(), currentUser.getRole());
                        session.setAttribute("message", "Forum thread and child comments purged from nodes.");
                    }
                    break;

                default:
                    session.setAttribute("error", "Unknown command entity mutation vector.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Transaction Execution Defect: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
}