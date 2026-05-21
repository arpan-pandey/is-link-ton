package com.islinkton.controller;

import com.islinkton.dao.CategoryDAO;
import com.islinkton.model.Category;
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

@WebServlet("/resources/*")
@MultipartConfig(maxFileSize = 50 * 1024 * 1024)
public class ResourceController extends HttpServlet {

    private ResourceService resourceService = new ResourceService();
    private CategoryDAO categoryDAO = new CategoryDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String pathInfo = request.getPathInfo();

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Resource> resources = resourceService.getAllResources();
                request.setAttribute("resources", resources);
                request.getRequestDispatcher("/pages/resources-list.jsp").forward(request, response);
            } else if (pathInfo.equals("/upload")) {
                if (!"Faculty".equalsIgnoreCase(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/resources");
                    return;
                }
                List<Category> resourceCategories = categoryDAO.getCategoriesByType("Course");
                request.setAttribute("categories", resourceCategories);
                request.getRequestDispatcher("/pages/upload-resource.jsp").forward(request, response);
            } else {
                response.sendRedirect(request.getContextPath() + "/resources");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error processing resource operation: " + e.getMessage());
            request.getRequestDispatcher("/pages/resources-list.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String pathInfo = request.getPathInfo();

        if (user == null || !"Faculty".equalsIgnoreCase(user.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        if (pathInfo != null && pathInfo.equals("/upload")) {
            String title = request.getParameter("title");
            String description = request.getParameter("description");
            String categoryIdStr = request.getParameter("categoryId");

            if (title == null || title.trim().isEmpty() || categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
                try {
                    request.setAttribute("error", "All fields are required.");
                    List<Category> resourceCategories = categoryDAO.getCategoriesByType("Resource");
                    request.setAttribute("categories", resourceCategories);
                    request.getRequestDispatcher("/pages/upload-resource.jsp").forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }

            try {
                Part filePart = request.getPart("file");
                if (filePart == null || filePart.getSize() == 0) {
                    request.setAttribute("error", "Please select a valid file to upload.");
                    List<Category> resourceCategories = categoryDAO.getCategoriesByType("Resource");
                    request.setAttribute("categories", resourceCategories);
                    request.getRequestDispatcher("/pages/upload-resource.jsp").forward(request, response);
                    return;
                }

                String fileName = FileUploadUtil.uploadResourceFile(filePart);
                if (fileName == null) {
                    request.setAttribute("error", "System failed to process resource file upload.");
                    List<Category> resourceCategories = categoryDAO.getCategoriesByType("Resource");
                    request.setAttribute("categories", resourceCategories);
                    request.getRequestDispatcher("/pages/upload-resource.jsp").forward(request, response);
                    return;
                }

                String fileExtension = getFileExtension(fileName);

                Resource resource = new Resource();
                resource.setTitle(title.trim());
                resource.setDescription(description != null ? description.trim() : "");
                resource.setFilePath(FileUploadUtil.FILES_DIR + "/" + fileName);
                resource.setFileType(fileExtension.toUpperCase());
                resource.setUploadedBy(user.getId());
                resource.setCategoryId(Integer.parseInt(categoryIdStr.trim()));

                boolean success = resourceService.uploadResource(resource);

                if (success) {
                    session.setAttribute("message", "Resource published successfully!");
                } else {
                    session.setAttribute("error", "Database repository failed to save resource link.");
                }

            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "System error: " + e.getMessage());
            }

            response.sendRedirect(request.getContextPath() + "/resources");
            return;
        }

        response.sendRedirect(request.getContextPath() + "/resources");
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "UNKNOWN";
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1).toUpperCase();
    }
}