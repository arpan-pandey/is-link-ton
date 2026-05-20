package com.islinkton.controller;

import com.islinkton.model.Resource;
import com.islinkton.model.User;
import com.islinkton.service.ResourceService;
import com.islinkton.utils.FileUploadUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.util.List;

@WebServlet("/resources")
@MultipartConfig(maxFileSize = 50 * 1024 * 1024)
public class ResourceController extends HttpServlet {

    private ResourceService resourceService = new ResourceService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            List<Resource> resources = resourceService.getAllResources();
            request.setAttribute("resources", resources);
            request.getRequestDispatcher("/pages/resources/list.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error loading resources: " + e.getMessage());
            request.getRequestDispatcher("/pages/resources/list.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null || !"Faculty".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String categoryIdStr = request.getParameter("categoryId");

        if (title == null || title.trim().isEmpty()) {
            request.setAttribute("error", "Title is required");
            request.getRequestDispatcher("/pages/resources/upload.jsp").forward(request, response);
            return;
        }
        if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
            request.setAttribute("error", "Please select a category");
            request.getRequestDispatcher("/pages/resources/upload.jsp").forward(request, response);
            return;
        }

        try {
            Part filePart = request.getPart("file");
            if (filePart == null || filePart.getSize() == 0) {
                request.setAttribute("error", "Please select a file");
                request.getRequestDispatcher("/pages/resources/upload.jsp").forward(request, response);
                return;
            }
            
            String fileName = FileUploadUtil.uploadResourceFile(filePart);

            if (fileName == null) {
                request.setAttribute("error", "File upload failed");
                request.getRequestDispatcher("/pages/resources/upload.jsp").forward(request, response);
                return;
            }

            String fileExtension = getFileExtension(fileName);

            Resource resource = new Resource();
            resource.setTitle(title.trim());
            resource.setDescription(description != null ? description.trim() : "");
            resource.setFilePath("assets/files/" + fileName);   // ← Updated path
            resource.setFileType(fileExtension.toUpperCase());
            resource.setUploadedBy(user.getId());
            resource.setCategoryId(Integer.parseInt(categoryIdStr));

            boolean success = resourceService.uploadResource(resource);

            if (success) {
                request.setAttribute("message", "Resource uploaded successfully!");
            } else {
                request.setAttribute("error", "Failed to save resource.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/resources");
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "UNKNOWN";
        }
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        return ext.toUpperCase();
    }
}