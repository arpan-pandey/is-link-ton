package com.islinkton.controller;

import com.islinkton.dao.PetitionDAO;
import com.islinkton.dao.ThreadDAO;
import com.islinkton.dao.ResourceDAO;
import com.islinkton.dao.VoteDAO;
import com.islinkton.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/dashboard")
public class UserDashboardController extends HttpServlet {
    private final ThreadDAO threadDAO = new ThreadDAO();
    private final PetitionDAO petitionDAO = new PetitionDAO();
    private final ResourceDAO resourceDAO = new ResourceDAO();
    private final VoteDAO voteDAO = new VoteDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Load base list content elements
            request.setAttribute("recentThreads", threadDAO.getRecentThreads());
            request.setAttribute("recentPetitions", petitionDAO.getRecentPetitions());
            request.setAttribute("recentResources", resourceDAO.getRecentResources());

            // append specific active interaction lists if a user session is running
            User user = (User) request.getSession().getAttribute("user");
            if (user != null) {
                request.setAttribute("votedThreadIds", voteDAO.getUserVotedThreadIds(user.getId()));
                request.setAttribute("votedPetitionIds", voteDAO.getUserVotedPetitionIds(user.getId()));
            }

            request.getRequestDispatcher("/pages/user-dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Dashboard failed to load framework assets.");
        }
    }
}