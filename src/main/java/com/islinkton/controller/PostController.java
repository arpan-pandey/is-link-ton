package com.islinkton.controller;

import java.util.List;
import java.io.IOException;

import com.islinkton.dao.PostDAO;
import com.islinkton.dao.ThreadDAO;
import com.islinkton.model.Post;
import com.islinkton.model.Thread;
import com.islinkton.model.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
                String parentPostIdStr = request.getParameter("parentPostId");
                
                if (content == null || content.trim().isEmpty()) {
                    session.setAttribute("error", "Post content cannot be empty.");
                } else {
                    Post post = new Post(threadId, user.getId(), content.trim());
                    
                    // binding parent id tracking info if it exists
                    if (parentPostIdStr != null && !parentPostIdStr.trim().isEmpty()) {
                        post.setParentPostId(Integer.parseInt(parentPostIdStr));
                    }
                    
                    postDAO.insertPost(post);
                    session.setAttribute("message", "Posted successfully!");
                }
                
            } else if ("delete".equals(action)) {
                String postIdStr = request.getParameter("postId");
                if (postIdStr != null) {
                    int postId = Integer.parseInt(postIdStr);
                    
                    boolean success = postDAO.deletePost(postId, user.getId() ,user.getRole());
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

        // always redirect back to the specific view-thread page
        response.sendRedirect(request.getContextPath() + "/threads/view?id=" + threadId);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        try {
            String threadIdStr = request.getParameter("id");
            if (threadIdStr == null || threadIdStr.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/threads");
                return;
            }
            
            int threadId = Integer.parseInt(threadIdStr);
            
            // 1. Fetch the main thread data 
            ThreadDAO threadDAO = new ThreadDAO();
            Thread thread = threadDAO.getThreadById(threadId);
            
            if (thread == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Thread not found");
                return;
            }

            PostDAO postDAO = new PostDAO();
            List<Post> posts = postDAO.getPostsByThread(threadId);
            
            // 2. Bind both objects to the request scope so JSTL can read them
            request.setAttribute("thread", thread);
            request.setAttribute("posts", posts); // 👈 This MUST match ${posts} in your JSP!
            
            // 3. Forward to your JSP view page
            request.getRequestDispatcher("/pages/thread-view.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}