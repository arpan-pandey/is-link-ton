<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login - Islinkton</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/login.css">
</head>
<body>

    <div class="login-container">
        <h1 class="logo">Islinkton</h1>
        <h2 class="welcome">Welcome Back</h2>
        <p class="subtitle">Sign in to your account to continue</p>

        <c:if test="${not empty error}">
            <div class="error">${error}</div>
        </c:if>

        <form action="login" method="post" id="form">
            <div class="form-group">
                <label>Email</label>
                <input type="email" name="email" required>
            </div>

            <div class="form-group">
                <label>Password</label>
                <input type="password" name="password" required>
            </div>

            <div class="forgot">
                <a href="#">Forgot Password?</a>
            </div>

            <button type="submit">Sign In</button>
        </form>

        <div class="register-link">
            Don't have an account? <a href="${pageContext.request.contextPath}/register">Register</a>
        </div>
    </div>

</body>
</html>