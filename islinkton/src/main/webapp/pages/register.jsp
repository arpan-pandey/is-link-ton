<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Islinkton</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/register.css">
</head>
<body>
     <c:if test="${not empty error}">
	    <div class="register-error-message" id="errorBox">
	        ${error}
	    </div>
	</c:if>
    <div class="register-container">
   		<h1 class="register-title">Islinkton</h1>
        <p class="subtitle">Create your academic profile</p>

<!-- 		<c:if test="${not empty sessionScope.success}">
		    <div class="success-message">
		        ${sessionScope.success}
		    </div>
		    <c:remove var="success" scope="session"/>  
		</c:if> -->
		
        <div class="register-box">
            <form action="${pageContext.request.contextPath}/register" method="post" enctype="multipart/form-data" id="registerForm">
                
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" placeholder="e.g; Jane Doe" required>
                </div>

                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" placeholder="e.g; janedoe67" required>
                </div>

                <div class="form-group">
                    <label for="email">University Email</label>
                    <input type="email" id="email" name="email" placeholder="e.g; jane.doe@islingtoncollege.edu.np" required>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Confirm Password</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" required>
                    </div>
                </div>

                <div class="form-group">
                    <label>Profile Image (Optional)</label>
                    <div class="upload-area" id="uploadArea">
                        <input type="file" id="profileImage" name="profileImage" accept="image/png, image/jpeg" style="display:none;">
                        <div class="upload-content">
                            <span class="upload-icon">📁</span>
                            <p>Upload a file or drag and drop</p>
                            <small>PNG, JPG up to 5MB</small>
                        </div>
                    </div>
                </div>

                <button type="submit" class="register-btn">Register Account</button>
            </form>

            <p class="signin-link">
                Already have an account? <a href="login.jsp">Sign in</a>
            </p>
        </div>
    </div>
</body>
<script>
    window.addEventListener("load", function () {
        const errorBox = document.getElementById("errorBox");

        if (errorBox) {
            setTimeout(() => {
                errorBox.classList.add("hide");
            }, 3000); // visible for 3 seconds
        }
    });
</script>
</html>