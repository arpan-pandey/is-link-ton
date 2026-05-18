<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="header">
    <div class="header-container">
        <div class="logo">
            <h2><a href="${pageContext.request.contextPath}/">Islinkton</a></h2>
        </div>
        
        <nav class="main-nav">
        	<!-- data-path attribute is added to each link for page specific highlighting -->
            <a href="${pageContext.request.contextPath}/dashboard" class="nav-link" data-path="/dashboard">Home</a>
            <a href="${pageContext.request.contextPath}/threads/" class="nav-link" data-path="/threads">Threads</a>
            <a href="${pageContext.request.contextPath}/petitions/" class="nav-link" data-path="/petitions">Petitions</a>
            <a href="${pageContext.request.contextPath}/groups/" class="nav-link" data-path="/groups">Study Groups</a>
            <a href="${pageContext.request.contextPath}/resources/" class="nav-link" data-path="/resources">Resources</a>
        </nav>

        <div class="header-right">
            <c:if test="${not empty user}">
                <a href="${pageContext.request.contextPath}/profile" class="profile-link">
                    <img src="${pageContext.request.contextPath}/uploads/profile/${user.profileImage != null ? user.profileImage : 'default.jpg'}" 
                         alt="Profile" class="avatar">
                </a>
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Logout</a>
            </c:if>
        </div>
    </div>
</header>

<script>
// when the page loads
document.addEventListener("DOMContentLoaded", function() {
    
	// get the current path from the browser bar (e.g., "/islinkton/threads/")
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll('.main-nav .nav-link');
    
    navLinks.forEach(link => {
        const dataPath = link.getAttribute('data-path');
        
        if (currentPath.includes(dataPath)) {
            // if current URL includes the dataPath (works for sub pages as well), lock the link in active status
            link.classList.add('nav-link-active');
        }
    });
});
</script>