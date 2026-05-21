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
import java.util.ArrayList;
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
        
        if (currentUser == null || !"Admin".equalsIgnoreCase(currentUser.getRole())) {
            session.setAttribute("error", "Access Denied: You do not possess Administrative Privileges.");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        try {
            List<User> pendingUsers = userDAO.getPendingUsers();
            List<User> approvedUsers = userDAO.getAllApprovedUsers();
            
            List<User> allUsers = new ArrayList<>();
            if (pendingUsers != null) {
                allUsers.addAll(pendingUsers);
            }
            if (approvedUsers != null) {
                allUsers.addAll(approvedUsers);
            }

            List<Petition> allPetitions = petitionDAO.getAllPetitions();
            List<Thread> allThreads = threadDAO.getPendingThreads();
            
            request.setAttribute("allUsers", allUsers);
            request.setAttribute("allPetitions", allPetitions);
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

        if (currentUser == null || !"Admin".equalsIgnoreCase(currentUser.getRole())) {
            session.setAttribute("error", "Unauthorized state alteration execution attempt.");
            response.sendRedirect(request.getContextPath() + "/home");
            return;
        }

        String targetType = request.getParameter("targetType");
        String action = request.getParameter("action");
        String idStr = request.getParameter("id");

        if (idStr == null || action == null || targetType == null) {
            session.setAttribute("error", "Invalid form modification transaction payload.");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
            return;
        }

        int targetId = Integer.parseInt(idStr);

        try {
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
                        petitionDAO.approvePetition(targetId);
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