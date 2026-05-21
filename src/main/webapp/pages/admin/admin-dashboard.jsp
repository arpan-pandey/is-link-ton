<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard - Islinkton</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/user-dashboard.css">
</head>
<body>
	<jsp:include page="/components/user-header.jsp" />
	
	<%-- Success Message Notification --%>
		<c:if test="${not empty sessionScope.message}">
		    <div id="successBox" class="popup-message success-toast">
		        <c:out value="${sessionScope.message}"/>
		    </div>
		    <c:remove var="message" scope="session" />
		</c:if>
		
		<%-- Error Message Notification --%>
		<c:if test="${not empty sessionScope.error}">
		    <div id="errorBox" class="popup-message">
		        <c:out value="${sessionScope.error}"/>
		    </div>
		    <c:remove var="error" scope="session" />
		</c:if>
	
	<main class="dashboard-main">
		<div class="page-titlebox">
	        <c:if test="${not empty user}">
				<!-- seperating first and last name -->
				<c:set var="nameParts" value="${fn:split(user.fullName, ' ')}" />
	            <h1 class="page-title">Welcome to Islinkton, <c:out value="${nameParts[0]}" />!</h1>
	        </c:if>

			<span class="page-subtitle">
				The central hub for academic discourse, student petitions, and collaborative resources.
				<br />Engage with your community below.
			</span>
		</div>
	</main>
	
	<jsp:include page="/components/footer.jsp" />
</body>
</html>
