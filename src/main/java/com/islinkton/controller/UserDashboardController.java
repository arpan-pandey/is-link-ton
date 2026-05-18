package com.islinkton.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.io.IOException;

import com.islinkton.dao.ThreadDAO;
import com.islinkton.model.Thread;


@WebServlet(asyncSupported = true, urlPatterns = { "/dashboard" })
public class UserDashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserDashboardController() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
            ThreadDAO threadDAO = new ThreadDAO();
            List<Thread> recentThreads = threadDAO.getRecentThreads();
            
            // attach list collection context to dashboard view scope
            request.setAttribute("recentThreads", recentThreads);
            
            // forward view down to your target dashboard JSP file
    		request.getRequestDispatcher("/pages/user-dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database connectivity error");
        }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
