<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<header class="header">
    <div class="header-container">
        <div class="logo">
            <h2><a href="${pageContext.request.contextPath}/">Islinkton</a></h2>
        </div>

        <nav class="main-nav">
            <a href="${pageContext.request.contextPath}/dashboard" class="nav-link" data-path="/dashboard">Home</a>
            <a href="${pageContext.request.contextPath}/threads/" class="nav-link" data-path="/threads">Threads</a>
            <a href="${pageContext.request.contextPath}/petitions/" class="nav-link" data-path="/petitions">Petitions</a>
            <a href="${pageContext.request.contextPath}/groups/" class="nav-link" data-path="/groups">Study Groups</a>
            <a href="${pageContext.request.contextPath}/resources/" class="nav-link" data-path="/resources">Resources</a>
        </nav>

        <div class="header-right">
            <c:if test="${not empty user}">
                <!-- Fixed Profile Link -->
                <a href="${pageContext.request.contextPath}/profile" class="profile-link">
                    <img src="${pageContext.request.contextPath}/${user.profileImage != null ? user.profileImage : 'uploads/profile/default.jpg'}" 
                         alt="Profile" 
                         class="avatar"
                         onerror="this.src='${pageContext.request.contextPath}/uploads/profile/default.jpg';">
                </a>
                
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Logout</a>
            </c:if>
        </div>
    </div>
</header>

<script>
// Highlight active nav link
document.addEventListener("DOMContentLoaded", function() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.main-nav .nav-link');

    navLinks.forEach(link => {
        const dataPath = link.getAttribute('data-path');
        if (currentPath.includes(dataPath)) {
            link.classList.add('nav-link-active');
        }
    });
});
</script>