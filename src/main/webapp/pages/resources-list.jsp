<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Resources - Islinkton</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/create-form.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/resources.css">
</head>
<body>
	<jsp:include page="/components/user-header.jsp" />
	
	<%-- Success Message Notification --%>
		<c:if test="${not empty message}">
		    <div id="successBox" class="popup-message success-toast">
		        <c:out value="${message}"/>
		    </div>
		    <c:remove var="message" scope="session" />
		</c:if>
		
		<%-- Error Message Notification --%>
		<c:if test="${not empty error}">
		    <div id="errorBox" class="popup-message">
		        <c:out value="${error}"/>
		    </div>
		    <c:remove var="error" scope="session" />
		</c:if>
	
	<main class="creation-container">
        
        <div class="creation-button-container">
			<div>
			<h1 class="page-title">Resources</h1>
				<span class="page-subtitle">
					Download the latest resources uploaded by teachers.
				</span>
			</div>
			<c:if test="${sessionScope.user.role eq 'Faculty'}">
			<div>
				<a class="creation-button" href="${pageContext.request.contextPath}/resources/upload">Upload Resource</a>
			</div>
			</c:if>
		</div>
        
        <div class="resources-card-container">
	        <c:forEach var="resource" items="${resources}">
	            <div class="card resource-card">
	            	<div class="icon-container center">
	                <a class="resource-icon center" href="${pageContext.request.contextPath}/getfile?path=${resource.filePath}">
	                    <c:out value="${resource.fileType}"/>
	                </a>
	                </div>
	                <div class="resource-card-main">
	                    <span class="resource-title"><c:out value="${resource.title}"/></span>
	                    <div>
	                        <span class="creator">By: <c:out value="${resource.authorUserName}"/></span>
	                        <span class="category-flair"><c:out value="${resource.categoryName}"/></span>
	                    </div>
	                </div>
	                <c:if test="${sessionScope.user.role eq 'Faculty'}">
						<!-- inline form (delete button only, but calls the DAO method) -->
                        <form action="${pageContext.request.contextPath}/resources/delete" method="post" 
                        	onsubmit="return confirm('Delete this resource?');" class="inline-form">
                            
                            <input type="hidden" name="id" value="${resource.id}">
                            <button type="submit" class="delete-item-btn">Remove</button>
                        </form>
					</c:if>
	            </div>
	        </c:forEach>
	    </div>
	</main>
	
	<jsp:include page="/components/footer.jsp" />
</body>
</html>