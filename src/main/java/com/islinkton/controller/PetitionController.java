package com.islinkton.controller;

import com.islinkton.dao.CategoryDAO;
import com.islinkton.dao.PetitionDAO;
import com.islinkton.dao.VoteDAO;
import com.islinkton.model.Category;
import com.islinkton.model.Petition;
import com.islinkton.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/petitions/*")
public class PetitionController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final PetitionDAO petitionDAO = new PetitionDAO();
    private final CategoryDAO categoryDAO = new CategoryDAO();
    private final VoteDAO voteDAO = new VoteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // list all approved petitions
                List<Petition> petitions = petitionDAO.getAllApprovedPetitions();
                request.setAttribute("petitions", petitions);
                
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                if (user != null) {
                    request.setAttribute("votedPetitionIds", voteDAO.getUserVotedPetitionIds(user.getId()));
                }
                
                request.getRequestDispatcher("/pages/petitions-list.jsp").forward(request, response);

            } else if (pathInfo.equals("/create")) {
                // show creation form
                List<Category> petitionCategories = categoryDAO.getCategoriesByType("Petition");
                request.setAttribute("categories", petitionCategories);
                request.getRequestDispatcher("/pages/create-petition.jsp").forward(request, response);
                
            } else {
                response.sendRedirect(request.getContextPath() + "/petitions");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading petitions dashboard: " + e.getMessage());
            request.getRequestDispatcher("/pages/petitions-list.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String pathInfo = request.getPathInfo();

        // Global Security Guard
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // creation
        if (pathInfo != null && pathInfo.equals("/create")) {
            String title = request.getParameter("title");
            String content = request.getParameter("content");
            String categoryIdStr = request.getParameter("categoryId");

            // server-side validation
            if (title == null || title.trim().isEmpty() || 
                content == null || content.trim().isEmpty() || 
                categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
                
                try {
                    request.setAttribute("error", "All fields are strictly required.");
                    List<Category> petitionCategories = categoryDAO.getCategoriesByType("Petition");
                    request.setAttribute("categories", petitionCategories);
                    request.getRequestDispatcher("/pages/create-petition.jsp").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            try {
                int categoryId = Integer.parseInt(categoryIdStr.trim());

                // Uses your target Model constructor signature perfectly
                Petition petition = new Petition(
                    title.trim(),
                    content.trim(),
                    user.getId(),
                    categoryId
                );

                boolean success = petitionDAO.createPetition(petition);

                if (success) {
                    session.setAttribute("message", "Petition submitted successfully! Pending approval.");
                } else {
                    session.setAttribute("error", "Database failed to persist submission.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "System error: " + e.getMessage());
            }

            // Post-Redirect-Get pattern safely points back to lists framework mapping
            response.sendRedirect(request.getContextPath() + "/petitions");
            return;
        }

        // delete
        if (pathInfo != null && pathInfo.equals("/delete")) {
            String petitionIdStr = request.getParameter("petitionId");

            if (petitionIdStr != null && !petitionIdStr.trim().isEmpty()) {
                try {
                    int petitionId = Integer.parseInt(petitionIdStr.trim());
                    boolean success = petitionDAO.deletePetition(petitionId, user.getRole());

                    if (success) {
                        session.setAttribute("message", "Petition deleted successfully.");
                    } else {
                        session.setAttribute("error", "Could not locate target petition records.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("error", "System failed to process deletion parameter processing.");
                }
            }
            response.sendRedirect(request.getContextPath() + "/petitions");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/petitions");
    }
}