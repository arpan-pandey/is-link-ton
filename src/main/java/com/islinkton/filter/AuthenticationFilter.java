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

        // public pages - allowed access
        if (uri.equals("/") ||
    	    uri.endsWith("/login") ||
    	    uri.endsWith("/register") ||
    	    uri.endsWith("/about") ||
    	    uri.endsWith("/contact") ||
    	    uri.contains("/css/") ||
    	    uri.contains("/assets/") ||
    	    uri.contains("/images/") ||
    	    uri.endsWith(".css") ||
    	    uri.endsWith(".jpg") ||
    	    uri.endsWith(".png") ||
    	    uri.endsWith(".jpeg")) {
		        
        		chain.doFilter(request, response);
		        return;
        }

        // no session - redirect to login
        if (session == null || session.getAttribute("user") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        User user = (User) session.getAttribute("user");

        // admin-only pages
        if (uri.contains("/admin") && !"admin".equalsIgnoreCase(user.getRole())) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        chain.doFilter(request, response);
    }
}