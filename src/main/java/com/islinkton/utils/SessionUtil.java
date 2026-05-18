package com.islinkton.utils;

import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {

    /**
     * Adds an attribute and sets a custom session timeout.
     * @param seconds Time before session expires due to inactivity.
     */
	// set session attribute
    public static void setAttribute(HttpServletRequest request, String name, Object value, int seconds) {
        HttpSession session = request.getSession(true);
        session.setAttribute(name, value);
        session.setMaxInactiveInterval(seconds);
    }
    
    // read session attribute
    public static Object getAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false);
        return (session != null) ? session.getAttribute(name) : null;
    }

    // remove session specific attribute
    public static void removeAttribute(HttpServletRequest request, String name) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(name);
        }
    }

    // remove all session at all
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}