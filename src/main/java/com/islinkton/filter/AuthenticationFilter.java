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
        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        String path = uri.substring(contextPath.length()); // cleaner path (/login, /css/styles.css, etc.)
        
        
        // public pages - allowed access
        if (path.equals("/home") ||
	        path.equals("/login") ||
	        path.equals("/register") ||
	        path.equals("/about") ||
	        path.equals("/contact") ||
	        path.startsWith("/css/") ||
	        path.startsWith("/assets/") ||
	        path.startsWith("/images/") ||
	        path.endsWith(".css") ||
	        path.endsWith(".jpg") ||
	        path.endsWith(".png") ||
	        path.endsWith(".jpeg")) {
	        
	        chain.doFilter(request, response);
	        return;
	    }
        
        HttpSession session = req.getSession(false);
        
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