package com.islinkton.controller;

import com.islinkton.model.Category;
import com.islinkton.model.Thread;
import com.islinkton.model.User;
import com.islinkton.model.Post;
import com.islinkton.service.ThreadService;
import com.islinkton.dao.CategoryDAO;
import com.islinkton.dao.PostDAO;
import com.islinkton.dao.VoteDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/threads/*")
public class ThreadController extends HttpServlet {

    private ThreadService threadService = new ThreadService();
    private final VoteDAO voteDAO = new VoteDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();   // e.g., threads/create, threads/view, threads/

        try {
            if (pathInfo == null || pathInfo.equals("/")) {
                
            	// list all threads in /threads
                List<Thread> threads = threadService.getAllApprovedThreads();
                request.setAttribute("threads", threads);
                
                HttpSession session = request.getSession();
                User user = (User) session.getAttribute("user");
                if (user != null) {
                    request.setAttribute("votedThreadIds", voteDAO.getUserVotedThreadIds(user.getId()));
                }
                
                request.getRequestDispatcher("/pages/threads-list.jsp").forward(request, response);
                

            } else if (pathInfo.equals("/create")) {
                
            	// show create form in /threads/create
            	CategoryDAO categoryDAO = new CategoryDAO();
            	List<Category> threadCategories = categoryDAO.getCategoriesByType("Thread");
            	request.setAttribute("categories", threadCategories);
            	
                request.getRequestDispatcher("/pages/create-thread.jsp").forward(request, response);

            } else if (pathInfo.equals("/view")) {
                
            	// view single thread single /threads/view?id=5
                String idStr = request.getParameter("id");
                if (idStr != null) {
                    int id = Integer.parseInt(idStr);
                    
                    Thread thread = threadService.getThreadById(id);
                    request.setAttribute("thread", thread);
                    
                    PostDAO postDAO = new PostDAO();
                    List<Post> posts = postDAO.getPostsByThread(id);
                    request.setAttribute("posts", posts);
                    
                    HttpSession session = request.getSession();
                    User user = (User) session.getAttribute("user");
                    if (user != null) {
                        request.setAttribute("votedThreadIds", voteDAO.getUserVotedThreadIds(user.getId()));
                    }
                    
                    request.getRequestDispatcher("/pages/view-thread.jsp").forward(request, response);
                } else {
                    response.sendRedirect(request.getContextPath() + "/threads");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error: " + e.getMessage());
            request.getRequestDispatcher("/pages/threads/list.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String pathInfo = request.getPathInfo();

        /*
         * COMMENT CREATION
         */
        if (pathInfo != null && pathInfo.equals("/comment/create")) {
            String threadIdStr = request.getParameter("threadId");
            String commentBody = request.getParameter("commentBody");

            // Basic validation checking
            if (commentBody == null || commentBody.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/threads");
                return;
            }

            try {
                int threadId = Integer.parseInt(threadIdStr.trim());

                Post post = new Post(threadId, user.getId(), commentBody.trim());
                PostDAO postDAO = new PostDAO();
                boolean success = postDAO.insertPost(post);

                // Redirect right back to view the exact thread detail page freshly updated
                response.sendRedirect(request.getContextPath() + "/threads/view?id=" + threadId);
            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding comment.");
            }
            return; // break execution out safely
        }
        
        /*
         * THREAD CREATION
         */
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String categoryIdStr = request.getParameter("categoryId");

        // null validations
        if (title == null || title.trim().isEmpty()) {
            request.setAttribute("error", "Thread title is required");
            request.getRequestDispatcher("/pages/create-thread.jsp").forward(request, response);
            return;
        }
        if (content == null || content.trim().isEmpty()) {
            request.setAttribute("error", "Content is required");
            request.getRequestDispatcher("/pages/threads/create.jsp").forward(request, response);
            return;
        }
        if (categoryIdStr == null || categoryIdStr.trim().isEmpty()) {
            request.setAttribute("error", "Please select a category");
            request.getRequestDispatcher("/pages/threads/create.jsp").forward(request, response);
            return;
        }

        try {
            int categoryId = Integer.parseInt(categoryIdStr);

            Thread thread = new Thread();
            thread.setTitle(title.trim());
            thread.setContent(content.trim());
            thread.setCategoryId(categoryId);
            thread.setAuthorId(user.getId());

            boolean success = threadService.createThread(thread);

            if (success) {
                request.setAttribute("message", "Thread posted successfully! Waiting for admin approval.");
            } else {
                request.setAttribute("error", "Failed to create thread.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Error creating thread: " + e.getMessage());
        }

        response.sendRedirect(request.getContextPath() + "/threads");
    }
}