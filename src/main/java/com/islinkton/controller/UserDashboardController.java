package com.islinkton.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.io.IOException;

import com.islinkton.dao.ThreadDAO;
import com.islinkton.dao.PetitionDAO;
import com.islinkton.model.Petition;
import com.islinkton.model.Thread;


@WebServlet(asyncSupported = true, urlPatterns = { "/dashboard" })
public class UserDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserDashboardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			// fetching from threads
            ThreadDAO threadDAO = new ThreadDAO();
            List<Thread> recentThreads = threadDAO.getRecentThreads();
            request.setAttribute("recentThreads", recentThreads);
            
            // fetching from petitions
            PetitionDAO petitionDAO = new PetitionDAO();
            List<Petition> recentPetitions = petitionDAO.getRecentPetitions();
            request.setAttribute("recentPetitions", recentPetitions);
            
            // forward view down to your the tageted dashboard JSP file
    		request.getRequestDispatcher("/pages/user-dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connectivity error");
        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}