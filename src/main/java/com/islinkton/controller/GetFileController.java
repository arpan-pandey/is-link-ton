package com.islinkton.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/getfile")
public class GetFileController extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String filePath = request.getParameter("path");   // e.g. "images/abc123_profile.jpg" or "files/xyz.pdf"

        if (filePath == null || filePath.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No file specified");
            return;
        }

        try {
        	String userHome = System.getProperty("user.home");
            String basePath = userHome + File.separator + "islinkton_uploads";
            
            File file = new File(basePath + File.separator + filePath);

            if (!file.exists() || file.isDirectory()) {
            	
            	String appDeployAssets = getServletContext().getRealPath("") + File.separator + "assets";
            	
                // Default profile image fallback
                if (filePath.startsWith("images/")) {
                    file = new File(appDeployAssets + File.separator + "images" + File.separator + "default.jpg");
                }
                if (!file.exists()) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
                    return;
                }
            }

            String mimeType = getServletContext().getMimeType(file.getName());
            if (mimeType == null) {
                mimeType = "application/octet-stream";
            }

            response.setContentType(mimeType);
            response.setHeader("Cache-Control", "public, max-age=3600");

            // Force download for non-image files
            if (!mimeType.startsWith("image/")) {
                response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
            }

            try (FileInputStream fis = new FileInputStream(file);
                 OutputStream os = response.getOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = fis.read(buffer)) != -1) {
                    os.write(buffer, 0, bytesRead);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error serving file");
        }
    }
}