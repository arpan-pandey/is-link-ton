package com.islinkton.controller;

import com.islinkton.dao.UserDAO;
import com.islinkton.model.User;
import com.islinkton.service.LoginService;
import com.islinkton.utils.CookieUtil;
import com.islinkton.utils.SessionUtil;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/login")
public class LoginController extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    String email = request.getParameter("email");
	    String password = request.getParameter("password");

	    LoginService service = new LoginService();
	    String status = service.login(email, password);

	    if ("Success".equals(status)) {
	        try {
	            UserDAO userDAO = new UserDAO();
	            User userData = userDAO.getUserByEmail(email);

	            if (userData == null) {
	                request.setAttribute("error", "User not found");
	                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
	                return;
	            }

	            // Set Session
	            SessionUtil.setAttribute(request, "user", userData, 3600);

	            // Set Last Login Cookie
	            String loginTime = LocalDateTime.now()
	                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss"));
	            CookieUtil.addCookie(response, "last_login", loginTime, 3600);

	            // Redirect based on role
	            if ("admin".equals(userData.getRole())) {
	            	response.sendRedirect(request.getContextPath() + "/admin/dashboard");
	            } else {
	            	response.sendRedirect(request.getContextPath() + "/student/dashboard");
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            request.setAttribute("error", "System error occurred");
	            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
	        }
	    } 
	    else {
	        // Handle all failure cases
	        request.setAttribute("error", status);   // e.g., "Password is incorrect"
	        request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
	    }
	}
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
    }
}