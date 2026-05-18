package com.islinkton.controller;

import com.islinkton.service.RegisterService;
import com.islinkton.utils.FileUploadUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;

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
    		
    		// empty file validation
            if (filePart == null || filePart.getSize() == 0) {
                request.getSession().setAttribute("error", "Please select an image to upload.");
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }

            // image file validation
            if (!FileUploadUtil.isImage(filePart)) {
                request.getSession().setAttribute("error", "Only image files (.jpg, .png, etc.) are allowed!");
                response.sendRedirect(request.getContextPath() + "/profile");
                return;
            }
            
            String fileName = null;
            
            if(FileUploadUtil.isImage(filePart)) {

                fileName = FileUploadUtil.uploadFile(filePart, request.getServletContext().getRealPath(""));
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