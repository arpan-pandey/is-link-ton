package com.islinkton.controller;

import com.islinkton.dao.PostDAO;
import com.islinkton.model.Post;
import com.islinkton.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/posts")
public class PostController extends HttpServlet {

    private PostDAO postDAO = new PostDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        String threadIdStr = request.getParameter("threadId");

        if (threadIdStr == null || threadIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/threads");
            return;
        }

        int threadId = Integer.parseInt(threadIdStr);

        try {
            if ("create".equals(action)) {
                String content = request.getParameter("content");
                
                if (content == null || content.trim().isEmpty()) {
                    session.setAttribute("error", "Post content cannot be empty.");
                } else {
                    Post post = new Post(threadId, user.getId(), content.trim());
                    boolean success = postDAO.insertPost(post);
                    if (success) {
                        session.setAttribute("message", "Reply posted successfully!");
                    } else {
                        session.setAttribute("error", "Failed to submit post.");
                    }
                }
                
            } else if ("delete".equals(action)) {
                String postIdStr = request.getParameter("postId");
                if (postIdStr != null) {
                    int postId = Integer.parseInt(postIdStr);
                    
                    // Optional security check: You could fetch the post first to verify 
                    // if (post.getUserId() == user.getId() || "ADMIN".equals(user.getRole()))
                    
                    boolean success = postDAO.deletePost(postId);
                    if (success) {
                        session.setAttribute("message", "Post deleted successfully.");
                    } else {
                        session.setAttribute("error", "Failed to delete post.");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "An error occurred: " + e.getMessage());
        }

        // 🔄 Always redirect back to the specific thread details view page
        response.sendRedirect(request.getContextPath() + "/threads/view?id=" + threadId);
    }
}