package com.islinkton.filter;

import com.islinkton.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})   // changed to protect everything
public class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        String uri = req.getRequestURI();

        // public pages - allow access
        if (uri.endsWith("/login") || uri.endsWith("/register") || 
            uri.contains("/css/") || uri.contains("/images/") || 
            uri.endsWith(".css") || uri.endsWith(".jpg") || uri.endsWith(".png")) {
            chain.doFilter(request, response);
            return;
        }

        // no session → redirect to login
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        // unapproved user (except admin)
        if (!user.isApproved() && !"admin".equalsIgnoreCase(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/pending-approval.jsp");
            return;
        }

        // admin-only pages
        if (uri.contains("/admin") && !"admin".equalsIgnoreCase(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}