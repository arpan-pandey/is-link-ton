<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="header">
    <div class="header-container">
        <div class="logo">
            <h2><a href="${pageContext.request.contextPath}/">Islinkton</a></h2>
        </div>
        
        <nav class="main-nav">
            <a href="${pageContext.request.contextPath}/" class="nav-link">Home</a>
            <a href="${pageContext.request.contextPath}/threads" class="nav-link active">Threads</a>
            <a href="${pageContext.request.contextPath}/petitions" class="nav-link">Petitions</a>
            <a href="${pageContext.request.contextPath}/groups" class="nav-link">Study Groups</a>
            <a href="${pageContext.request.contextPath}/resources" class="nav-link">Resources</a>
        </nav>

        <div class="header-right">
            <c:if test="${not empty user}">
                <a href="${pageContext.request.contextPath}/profile" class="profile-link">
                    <img src="${pageContext.request.contextPath}/uploads/profile/${user.profileImage != null ? user.profileImage : 'default.jpg'}" 
                         alt="Profile" class="avatar">
                    <span>${user.fullName}</span>
                </a>
                <a href="${pageContext.request.contextPath}/logout" class="logout-btn">Logout</a>
            </c:if>
        </div>
    </div>
</header>