<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Islinkton - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/index.css">
</head>
<body>

    <main class="hero-container">
        <div class="hero-content">
            <h1 class="brand-title">Islinkton</h1>
            
            <h2 class="hero-title">The Future of Academic Collaboration.</h2>
            <p class="hero-subtitle">Connect, collaborate, and excel with the premier digital ecosystem for students.</p>
            
            <div class="hero-actions">
                <a href="${pageContext.request.contextPath}/login" class="btn btn-outline">Login</a>
                <a href="${pageContext.request.contextPath}/register" class="btn btn-filled">Register</a>
            </div>
        </div>
    </main>

    <section class="features-grid">
        <div class="feature-card item-count-card">
            <div class="card-inner-content">
                <span class="metric-icon">&#9881;</span>
                <h3 class="metric-value">10k+</h3>
                <p class="metric-label">Students</p>
            </div>
        </div>
        
        <div class="feature-card">
            <div class="card-inner-content">
                <span class="metric-icon">&#128101;</span>
                <h3 class="metric-value">Groups</h3>
                <p class="metric-label">Study circles</p>
            </div>
        </div>
        
        <div class="feature-card">
            <div class="card-inner-content">
                <span class="metric-icon">&#128394;</span>
                <h3 class="metric-value">Petitions</h3>
                <p class="metric-label">Lead change</p>
            </div>
        </div>
        
        <div class="feature-card">
            <div class="card-inner-content">
                <span class="metric-icon">&#128196;</span>
                <h3 class="metric-value">Resources</h3>
                <p class="metric-label">Notes & archives</p>
            </div>
        </div>
    </section>

</body>
</html>