package com.islinkton.controller;

import com.islinkton.dao.UserDAO;
import com.islinkton.model.User;
import com.islinkton.utils.FileUploadUtil;
import com.islinkton.utils.PasswordUtil;
import com.islinkton.utils.SessionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;

@WebServlet("/profile/*")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 2,
    maxFileSize = 50L * 1024 * 1024,      // 50 MB
    maxRequestSize = 60L * 1024 * 1024    // 60 MB
)
public class ProfileController extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String pathInfo = request.getPathInfo();
        
        // Handle fallback redirect for anything other than basic root GET profile view
        if (pathInfo != null && !pathInfo.equals("/")) {
            response.sendRedirect(request.getContextPath() + "/profile/");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("/pages/user-profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String pathInfo = request.getPathInfo();

        try {
            // Check for modern subpath explicitly first
            if (pathInfo != null && pathInfo.equals("/deactivate")) {
                handleDeactivateAccount(request, response, user);
                return; // Return directly; method manages its own out-bound redirections
            }

            // Fallback strategy pattern checks parameters for form variants
            String action = request.getParameter("action");
            if ("changePassword".equals(action)) {
                handleChangePassword(request, user);
            } else {
                handleUpdateProfile(request, user);
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "An error occurred: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/profile/");
    }

    private void handleUpdateProfile(HttpServletRequest request, User user) throws Exception {
        HttpSession session = request.getSession();
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");

        if (fullName == null || fullName.trim().isEmpty()) {
            session.setAttribute("error", "Full name is required");
            return;
        }

        if (!fullName.trim().equals(user.getFullName()) || !email.trim().equals(user.getEmail())) {
            int rows = userDAO.updateUserProfile(user.getId(), fullName.trim(), email);
            if (rows > 0) {
                user.setFullName(fullName.trim());
                user.setEmail(email);
            }
        }

        Part filePart = request.getPart("profileImage");
        if (filePart != null && filePart.getSize() > 0) {
            if (FileUploadUtil.isImage(filePart)) {
                String newFileName = FileUploadUtil.uploadProfileImage(filePart);

                if (newFileName != null) {
                    user.setProfileImage(newFileName);
                    userDAO.updateProfileImage(user.getId(), newFileName);
                    System.out.println("[ProfileController] Database updated with file: " + newFileName);
                }
            } else {
                session.setAttribute("error", "Only image files (JPG, PNG, etc.) are allowed.");
                return;
            }
        }

        SessionUtil.setAttribute(request, "user", user, 3600);
        session.setAttribute("message", "Profile updated successfully!");
    }

    private void handleChangePassword(HttpServletRequest request, User user) throws Exception {
        HttpSession session = request.getSession();
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (!newPassword.equals(confirmPassword)) {
            session.setAttribute("error", "New passwords do not match.");
        } else if (newPassword.length() < 6) {
            session.setAttribute("error", "Password must be at least 6 characters long.");
        } else if (PasswordUtil.checkPassword(currentPassword, user.getPassword())) {

            String hashedNewPassword = PasswordUtil.hashPassword(newPassword);
            int result = userDAO.updatePassword(user.getId(), hashedNewPassword);

            if (result > 0) {
                user.setPassword(hashedNewPassword);
                SessionUtil.setAttribute(request, "user", user, 3600);
                session.setAttribute("message", "Password updated successfully!");
            } else {
                session.setAttribute("error", "Failed to update password.");
            }
        } else {
            session.setAttribute("error", "Current password is incorrect.");
        }
    }

    private void handleDeactivateAccount(HttpServletRequest request, HttpServletResponse response, User user) throws Exception {
        HttpSession session = request.getSession();

        // Security role guard check
        if (!"Student".equalsIgnoreCase(user.getRole())) {
            session.setAttribute("error", "Unauthorized action. Only students can self-deactivate.");
            response.sendRedirect(request.getContextPath() + "/profile/");
            return;
        }

        // Execute the database update payload statement status flag change 
        int result = userDAO.deactivateUser(user.getId());

        if (result > 0) {
            // Nuke active session footprint completely
            session.invalidate();
            
            // Build a fresh new session container to pass flash toast over redirect
            HttpSession outboundSession = request.getSession(true);
            outboundSession.setAttribute("message", "Your account has been successfully deactivated.");
            
            response.sendRedirect(request.getContextPath() + "/login");
        } else {
            session.setAttribute("error", "Failed to process account deactivation in backend databases.");
            response.sendRedirect(request.getContextPath() + "/profile/");
        }
    }
}