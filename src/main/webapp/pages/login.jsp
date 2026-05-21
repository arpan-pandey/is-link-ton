<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Islinkton</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/login.css">
</head>
<body class="center">

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

    <div class="login-container">
    
        <h1 class="login-title">Islinkton</h1>
        <p class="subtitle">An academic discourse platform</p>
    	
    	<div class="login-box">
	
	        <form action="${pageContext.request.contextPath}/login" method="post" id="loginForm">
	            
	            <div class="form-group">
	                <label for="email">Email</label>
	                <input type="email" name="email" id="email" placeholder="e.g; janedoe67" required>
	            </div>
	            
	            <div class="form-group">
	                <label for="password">Password</label>
	                <input type="password" name="password" id="password" required>
	            </div>
	
	            <button type="submit" class="login-btn">Access Portal</button>
	        </form>
	
	        <p class="signup-link">
	            Don't have an account? <a href="${pageContext.request.contextPath}/register">Sign up</a>
	        </p>
	    </div>
    </div>

</body>

<script>
window.addEventListener("load", function () {
    const errorBox = document.getElementById("errorBox");
    const successBox = document.getElementById("successBox");

    if (errorBox) {
        setTimeout(() => {
            errorBox.classList.add("hide");
        }, 3000);
    }

    if (successBox) {
        setTimeout(() => {
            successBox.classList.add("hide");
        }, 3000);
    }
});
</script>
</html>