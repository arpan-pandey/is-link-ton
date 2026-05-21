package com.islinkton.controller;

import java.io.IOException;

import com.islinkton.dao.UserDAO;
import com.islinkton.service.RegisterService;
import com.islinkton.utils.FileUploadUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

@WebServlet("/register")
@MultipartConfig(maxFileSize = 5 * 1024 * 1024)
public class RegisterController extends HttpServlet {
	
	private UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    	String fullName = request.getParameter("fullName");
    	String username = request.getParameter("username");
    	String email = request.getParameter("email");
    	String password = request.getParameter("password");
    	String confirm = request.getParameter("confirmPassword");
    	
    	if (fullName == null || fullName.trim().isEmpty() ||
			username == null || username.trim().isEmpty() || 
            email == null || email.trim().isEmpty() || 
            password == null || password.trim().isEmpty() ||
            confirm == null || confirm.trim().isEmpty()) {
            
            request.setAttribute("error", "Registration Rejected: Complete all input parameters accurately.");
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
            
            return;
        }
        
        if (!password.equals(confirm)) {
    		request.setAttribute("error", "Passwords do not match");
    		request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
    		return;
    	}
    	
        try {

            Part filePart = request.getPart("profileImage");
            String profileImageName = null;

            if (filePart != null && filePart.getSize() > 0) {
                if (!FileUploadUtil.isImage(filePart)) {
                    request.setAttribute("error", "Only image files are allowed!");
                    request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
                    return;
                }
                
                profileImageName = FileUploadUtil.uploadProfileImage(filePart);
            }

            RegisterService service = new RegisterService();
            service.registerUser(fullName, username, email, password, profileImageName);

            request.getSession().setAttribute("success", "Account created successfully!");
            response.sendRedirect("login");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
    }
}