package com.islinkton.controller;

import com.islinkton.service.RegisterService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@WebServlet("/register")
@jakarta.servlet.annotation.MultipartConfig
public class RegisterController extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	
    	try {
    		
    		String fullName = request.getParameter("fullName");
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirmPassword");
            
            if (!password.equals(confirm)) {
                request.setAttribute("error", "Passwords do not match");
                request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
                return;
            }
    		
    		Part filePart = request.getPart("profileImage");

            String fileName = null;

            if (filePart != null && filePart.getSize() > 0) {

                fileName = System.currentTimeMillis() + "_" + filePart.getSubmittedFileName();

                String uploadPath = request.getServletContext().getRealPath("") + File.separator + "uploads";

                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) uploadDir.mkdir();

                try (InputStream input = filePart.getInputStream()) {
                    Files.copy(input,
                            Paths.get(uploadPath + File.separator + fileName),
                            StandardCopyOption.REPLACE_EXISTING);
                }
            }
            RegisterService service = new RegisterService();
            service.registerUser(fullName, username, email, password, fileName);
            
            request.getSession().setAttribute("success", "Account created successfully!");
            response.sendRedirect("login");
            return;
    		
    	}
    	catch (Exception e) {
    		request.setAttribute("error", e.getMessage());
    	    request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
    	}

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
    }
}