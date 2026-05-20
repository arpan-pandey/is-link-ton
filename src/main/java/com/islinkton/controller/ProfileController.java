package com.islinkton.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import com.islinkton.dao.UserDAO;
import com.islinkton.model.User;
import com.islinkton.utils.PasswordUtil;
import com.islinkton.utils.SessionUtil;
import com.islinkton.utils.FileUploadUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/profile")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,   // 2MB
    maxFileSize = 1024 * 1024 * 5,         // 5MB
    maxRequestSize = 1024 * 1024 * 10      // 10MB
)
public class ProfileController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/pages/user-profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        User user = (User) SessionUtil.getAttribute(request, "user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");

        try {
            if ("changePassword".equals(action)) {
                // ==================== CHANGE PASSWORD ====================
                String currentPassword = request.getParameter("currentPassword");
                String newPassword = request.getParameter("newPassword");
                String confirmPassword = request.getParameter("confirmPassword");

                if (!newPassword.equals(confirmPassword)) {
                    request.setAttribute("error", "New passwords do not match.");
                } else if (newPassword.length() < 6) {
                    request.setAttribute("error", "Password must be at least 6 characters long.");
                } else if (PasswordUtil.checkPassword(currentPassword, user.getPassword())) {
                    
                    String hashedNewPassword = PasswordUtil.hashPassword(newPassword);
                    int result = userDAO.updatePassword(user.getId(), hashedNewPassword);

                    if (result > 0) {
                        user.setPassword(hashedNewPassword);
                        SessionUtil.setAttribute(request, "user", user, 3600);
                        request.setAttribute("message", "Password updated successfully!");
                    } else {
                        request.setAttribute("error", "Failed to update password.");
                    }
                } else {
                    request.setAttribute("error", "Current password is incorrect.");
                }

            } else {
                // ==================== UPDATE PROFILE + IMAGE ====================
                String firstName = getPartValue(request, "firstName");
                String lastName = getPartValue(request, "lastName");
                String email = getPartValue(request, "email");

                String fullName = (firstName != null ? firstName.trim() : "") + " " +
                                 (lastName != null ? lastName.trim() : "");
                fullName = fullName.trim();

                // Update name and email
                userDAO.updateUserProfile(user.getId(), fullName, email);
                user.setFullName(fullName);
                user.setEmail(email);

                // Handle Profile Image Upload
                Part filePart = request.getPart("profileImage");
                if (filePart != null && filePart.getSize() > 0) {
                    if (FileUploadUtil.isImage(filePart)) {

                        String uploadPath = getServletContext().getRealPath("/uploads/profile/");
                        File uploadDir = new File(uploadPath);
                        if (!uploadDir.exists()) {
                            uploadDir.mkdirs();
                        }

                        String savedFileName = FileUploadUtil.uploadFile(filePart, uploadPath);

                        if (savedFileName != null) {
                            // Store full relative path in database
                            String imagePath = "uploads/profile/" + savedFileName;
                            
                            userDAO.updateProfileImage(user.getId(), imagePath);
                            user.setProfileImage(imagePath);
                        }
                    } else {
                        request.setAttribute("error", "Only image files (JPG, PNG, GIF) are allowed.");
                    }
                }

                SessionUtil.setAttribute(request, "user", user, 3600);
                request.setAttribute("message", "Profile updated successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating profile.");
        }

        doGet(request, response); // Refresh the page
    }

    // Helper method to read text fields from multipart request
    private String getPartValue(HttpServletRequest request, String fieldName) 
            throws IOException, ServletException {
        Part part = request.getPart(fieldName);
        if (part == null || part.getSize() == 0) {
            return null;
        }
        return new String(part.getInputStream().readAllBytes(), StandardCharsets.UTF_8).trim();
    }
}