<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Islinkton - The Future of Academic Collaboration</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/index.css">
</head>
<body>

    <div class="page-content">
        <main class="hero-container">
            <div class="hero-content">
                <h1 class="brand-title">Islinkton</h1>
                <h2 class="hero-title">The Future of Academic Collaboration.</h2>
                <p class="hero-subtitle">Connect, collaborate, and excel with the premier digital ecosystem for students.</p>
                
                <div class="hero-actions">
                    <a href="${pageContext.request.contextPath}/login" class="btn btn-outline">Login</a>
                    <a href="${pageContext.request.contextPath}/register" class="btn btn-filled">Register</a>
                </div>

                <div class="divider-line"></div>

                <div class="hero-footer-links">
                    <a href="${pageContext.request.contextPath}/about" class="secondary-link">About Us</a>
                    <a href="${pageContext.request.contextPath}/contact" class="secondary-link">Contact Us</a>
                </div>
            </div>
        </main>
    </div>

</body>
</html>