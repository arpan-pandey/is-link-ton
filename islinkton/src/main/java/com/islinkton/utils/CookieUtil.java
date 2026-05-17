package com.islinkton.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

	/**
     * Creates and adds a cookie to the response.
     * @param response The HttpServletResponse object
     * @param name     The name of the cookie
     * @param value    The value of the cookie
     * @param maxAge   Max age in seconds (e.g., 3600 for 1 hour)
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int min) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(min);
        
        // Security Best Practices
        cookie.setHttpOnly(true); // Prevents XSS access to the cookie
        // cookie.setSecure(true); // Uncomment if using HTTPS
        
        response.addCookie(cookie);
    }

    /**
     * Deletes a cookie by setting its max age to 0.
     * @param response The HttpServletResponse object
     * @param name     The name of the cookie to delete
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setPath("/");
        cookie.setMaxAge(0); // This tells the browser to delete it immediately
        response.addCookie(cookie);
    }

    /**
     * Optional: Helper to retrieve a cookie value by name
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        if (request == null) return null;
        
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}