package com.islinkton.controller;

import com.islinkton.dao.CategoryDAO;
import com.islinkton.dao.PetitionDAO;
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo(); // e.g., /petitions/create, /petitions/

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                // list all approved petitions in /petitions
                List<Petition> petitions = petitionDAO.getAllApprovedPetitions();
                request.setAttribute("petitions", petitions);
                request.getRequestDispatcher("/pages/petitions-list.jsp").forward(request, response);

            } else if (pathInfo.equals("/create")) {
            	CategoryDAO categoryDAO = new CategoryDAO();
            	List<Category> threadCategories = categoryDAO.getCategoriesByType("Petition");
            	request.setAttribute("categories", threadCategories);
            	
                request.getRequestDispatcher("/pages/create-petition.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/petitions");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading petitions: " + e.getMessage());
            request.getRequestDispatcher("/pages/petitions-list.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.equals("/create")) {
            String title = request.getParameter("title");
            String content = request.getParameter("content");

        // Null and empty checking validations
        if (title == null || title.trim().isEmpty()) {
            request.setAttribute("error", "Petition title is required");
            request.getRequestDispatcher("/pages/create-petition.jsp").forward(request, response);
            return;
        }
        if (content == null || content.trim().isEmpty()) {
            request.setAttribute("error", "Petition content is required");
            request.getRequestDispatcher("/pages/create-petition.jsp").forward(request, response);
            return;
        }

        try {
            // using constructor
            Petition petition = new Petition(title.trim(), content.trim(), user.getUsername());
            boolean success = petitionDAO.createPetition(petition);

            if (success) {
                session.setAttribute("message", "Petition submitted successfully!");
            } else {
                session.setAttribute("error", "Failed to submit petition.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "System error: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/petitions");
        return;
    }

        if (pathInfo != null && pathInfo.equals("/delete")) {
            String petitionIdStr = request.getParameter("petitionId");

            if (petitionIdStr != null && !petitionIdStr.trim().isEmpty()) {
                try {
                    int petitionId = Integer.parseInt(petitionIdStr.trim());
                    
                    boolean success = petitionDAO.deletePetition(petitionId);
                    
                    if (success) {
                        session.setAttribute("message", "Petition removed successfully.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("error", "Failed to delete petition.");
                }
            }
            response.sendRedirect(request.getContextPath() + "/petitions");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/petitions");
    }
}