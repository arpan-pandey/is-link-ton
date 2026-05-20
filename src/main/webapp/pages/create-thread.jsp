<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Create Thread - Islinkton</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/threads.css">
</head>
<body>
    <jsp:include page="/components/user-header.jsp" />
    
    <main class="form-main-container">
        <div class="form-card">
            <div class="form-header">
                <h2>Create a New Thread</h2>
                <p>Share your ideas, questions, or resources with the Islinkton student community.</p>
            </div>
            
            <c:if test="${not empty errorMessage}">
                <div class="alert alert-danger">
                    <c:out value="${errorMessage}" />
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/threads/create" method="POST" class="academic-form">
                
                <div class="form-group">
                    <label for="threadTitle">Thread Title</label>
                    <input 
                        type="text" 
                        id="threadTitle" 
                        name="title" 
                        required
                        placeholder="e.g., 'Questions regarding the upcoming Midterm format'" 
                    />
                </div>

                <div class="form-group">
                    <label for="threadCategory">Category Flair</label>
                    <select id="threadCategory" name="categoryId" required>
                        <option value="" disabled selected>Select a Suitable Flair</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.id}" ${param.categoryId == category.id ? 'selected' : ''}>
                                <c:out value="${category.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
                    <label for="threadContent">Body Content</label>
                    <textarea 
                        id="threadContent" 
                        name="content" 
                        rows="8" 
                        placeholder="Provide details, background context, or instructions here..." 
                        required
                    ><c:out value="${param.content}" /></textarea>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Publish Thread</button>
                    <a href="${pageContext.request.contextPath}/threads" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </main>
    
    <jsp:include page="/components/footer.jsp" />
</body>
</html>