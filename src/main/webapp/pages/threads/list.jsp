<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Threads - Islinkton</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <style>
        .main-content { max-width: 800px; margin: 0 auto; }
        .create-btn {
            background: #1f2937;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
        }
        .create-btn:hover { background: #0066cc; }
    </style>
</head>
<body>
    <jsp:include page="../../components/student-header.jsp" />

    <div class="container" style="max-width: 1200px; margin: 2rem auto; padding: 0 20px; display: flex; gap: 2rem;">
        
        <!-- main feed -->
        <div class="main-content">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 1.5rem;">
                <h1>Threads</h1>
                <a href="${pageContext.request.contextPath}/threads/create" class="create-btn">+ Create Thread</a>
            </div>

            <c:if test="${not empty message}">
                <p style="color: green; background: #f0fdf4; padding: 10px; border-radius: 6px;">${message}</p>
            </c:if>
            <c:if test="${not empty error}">
                <p style="color: red; background: #fef2f2; padding: 10px; border-radius: 6px;">${error}</p>
            </c:if>

            <c:forEach var="thread" items="${threads}">
                <div class="thread-card">
                    <div class="thread-meta">
                        <span class="category-tag">${thread.categoryName}</span>
                        <span>Posted by ${thread.authorName}</span>
                        <span>• ${thread.createdAt}</span>
                    </div>
                    <h3><a href="${pageContext.request.contextPath}/threads/view?id=${thread.id}" style="text-decoration:none; color:inherit;">
                        ${thread.title}
                    </a></h3>
                    <p class="thread-content">${thread.content}</p>
                    <div class="thread-stats">
                        <span>👍 ${thread.upvotes != null ? thread.upvotes : 0}</span>
                        <span>💬 Comments</span>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty threads}">
                <p>No threads yet. Be the first to post!</p>
            </c:if>
        </div>

        <!-- Sidebar -->
        <div style="width: 280px; flex-shrink: 0;">
            <div class="thread-card">
                <h4>About Islinkton Threads</h4>
                <p>The central hub for academic discourse, campus news, and student-led initiatives.</p>
            </div>
        </div>
    </div>

    <jsp:include page="../../components/footer.jsp" />
</body>
</html>