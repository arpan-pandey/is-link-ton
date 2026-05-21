package com.islinkton.filter;

import com.islinkton.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String contextPath = req.getContextPath();
        String uri = req.getRequestURI();
        
        String path = uri.substring(contextPath.length());
        
        // normalize path strings to strip any accidental double leading slashes
        if (path.startsWith("//")) {
            path = path.substring(1);
        }
        
        // fast-track assets by checking extensions right at the top
        if (path.endsWith(".css") || 
            path.endsWith(".js") || 
            path.endsWith(".jpg") || 
            path.endsWith(".jpeg") || 
            path.endsWith(".png") || 
            path.endsWith(".gif") || 
            path.contains("/assets/") ||
            path.startsWith("/getfile")) { // making sure students can hit the download engine stream
            
            chain.doFilter(request, response);
            return;
        }
        
        // public pages - allowed access
        if (path.equals("/") ||
            path.equals("/home") ||
            path.equals("/login") ||
            path.equals("/register") ||
            path.equals("/about") ||
            path.equals("/contact")) {
            
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