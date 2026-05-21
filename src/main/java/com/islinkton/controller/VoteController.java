package com.islinkton.controller;

import com.islinkton.dao.VoteDAO;
import com.islinkton.model.User;
import com.islinkton.model.Vote;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/vote/*")
public class VoteController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final VoteDAO voteDAO = new VoteDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String pathInfo = request.getPathInfo();

        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String targetIdStr = request.getParameter("id");
        if (targetIdStr == null || targetIdStr.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/dashboard");
            return;
        }

        try {
            int targetId = Integer.parseInt(targetIdStr.trim());
            String typeFlag = null;

            if (pathInfo != null && pathInfo.equals("/thread")) {
                typeFlag = "THREAD";
            } else if (pathInfo != null && pathInfo.equals("/petition")) {
                typeFlag = "PETITION";
            }

            if (typeFlag != null) {
                Vote vote = new Vote(targetId, user.getId(), typeFlag);
                voteDAO.toggleVote(vote);
            }

            String referer = request.getHeader("Referer");
            if (referer != null && !referer.isEmpty()) {
                response.sendRedirect(referer);
            } else {
                response.sendRedirect(request.getContextPath() + (pathInfo.equals("/thread") ? "/threads" : "/petitions"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Processing vote action failed.");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doPost(request, response);
    }
}