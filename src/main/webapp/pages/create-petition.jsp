<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Petition - Islinkton</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/create-form.css">
</head>
<body>
    <jsp:include page="/components/user-header.jsp" />
    
    <main class="form-main-container">
        <div class="form-card">
            <div class="form-header">
                <h2>Create a New Petition</h2>
                <p>Start a petition to voice what matters to the student community.</p>
            </div>
            
            <%-- error message --%>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">
                    <c:out value="${error}" />
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/petitions/create" method="POST" class="academic-form">
                
                <div class="form-group">
                    <label for="petitionTitle">Petition Title</label>
                    <input 
                        type="text" 
                        id="petitionTitle" 
                        name="title" 
                        required
                        placeholder="e.g., 'Betterment of Existing Attendance System Modules'" 
                    />
                </div>
                
                <div class="form-group">
                    <label for="petitionContent">Petition Content</label>
                    <input 
                        id="petitionContent" 
                        name="content"
                        type="text" 
                        required
                        placeholder="Describe why the issue matters, and the changes expected..."
                    ></input>
                </div>

                <div class="form-group">
                    <label for="petitionCategory">Category Flair</label>
                    <select id="petitionCategory" name="categoryId" required>
                        <option value="" disabled selected>Select a Suitable Flair</option>
                        
                        <%-- binding id to value and printing names --%>
                        <c:forEach var="c" items="${categories}">
                            <option value="${c.id}">
                                <c:out value="${c.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Start Petition</button>
                    <a href="${pageContext.request.contextPath}/petitions" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </main>
    
    <jsp:include page="/components/footer.jsp" />
</body>
</html>