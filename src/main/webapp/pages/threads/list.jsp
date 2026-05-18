<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Threads - Islinkton</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/threads-list.css">
</head>
<body>
    <jsp:include page="../../components/student-header.jsp" />

    <div class="threads-container">
        
        <main class="main-content">
            <div class="feed-header">
                <h1>Threads</h1>
                <a href="${pageContext.request.contextPath}/threads/create" class="create-btn">+ Create Thread</a>
            </div>

            <c:if test="${not empty message}">
                <p class="alert-msg alert-success">${message}</p>
            </c:if>
            <c:if test="${not empty error}">
                <p class="alert-msg alert-error">${error}</p>
            </c:if>

            <c:forEach var="thread" items="${threads}">
                <div class="thread-card">
                    <div class="thread-meta">
                        <span class="category-tag">${thread.categoryName}</span>
                        <span class="meta-author">Posted by ${thread.authorName}</span>
                        <span class="meta-date">• ${thread.createdAt}</span>
                    </div>
                    
                    <h3 class="thread-title">
                        <a href="${pageContext.request.contextPath}/threads/view?id=${thread.id}">
                            ${thread.title}
                        </a>
                    </h3>
                    
                    <p class="thread-excerpt">${thread.content}</p>
                    
                    <div class="thread-stats">
                        <span class="stat-item">👍 ${thread.upvotes != null ? thread.upvotes : 0}</span>
                        <span class="stat-item">💬 Comments</span>
                    </div>
                </div>
            </c:forEach>

            <c:if test="${empty threads}">
                <p class="empty-state-text">No threads yet. Be the first to post!</p>
            </c:if>
        </main>

        <aside class="sidebar-content">
            <div class="thread-card info-card">
                <h4>About Islinkton Threads</h4>
                <p>The central hub for academic discourse, campus news, and student-led initiatives.</p>
            </div>
        </aside>
    </div>

    <jsp:include page="../../components/footer.jsp" />
</body>
</html>