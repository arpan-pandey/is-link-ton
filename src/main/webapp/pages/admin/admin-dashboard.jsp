<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Islinkton Forum</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
</head>
<body>
    <div class="admin-container">
        <h1>👨‍💼 Admin Dashboard</h1>
        <p>Welcome, ${user.fullName}</p>

        <!-- Success / Error Messages -->
        <c:if test="${not empty message}">
            <p style="color:green;">${message}</p>
        </c:if>
        <c:if test="${not empty error}">
            <p style="color:red;">${error}</p>
        </c:if>

        <h2>Pending User Approvals</h2>
        
        <c:if test="${empty pendingUsers}">
            <p>No pending registrations.</p>
        </c:if>

		<c:forEach var="pending" items="${pendingUsers}">
		    <div class="user-card">
		        <p><strong>${pending.fullName}</strong> (${pending.email})</p>
		        <p>Role: ${pending.role}</p>
		        
		        <form action="${pageContext.request.contextPath}/admin/dashboard" method="post" style="display:inline;">
		            <input type="hidden" name="userId" value="${pending.id}">
		            <input type="hidden" name="action" value="approve">
		            <button type="submit" class="btn-approve">Approve</button>
		        </form>
		
		        <form action="${pageContext.request.contextPath}/admin/dashboard" method="post" style="display:inline;">
		            <input type="hidden" name="userId" value="${pending.id}">
		            <input type="hidden" name="action" value="reject">
		            <button type="submit" class="btn-reject">Reject</button>
		        </form>
		    </div>
		</c:forEach>

        <hr>
        <h2>All Approved Users</h2>
        <ul>
            <c:forEach var="user" items="${approvedUsers}">
                <li>${user.fullName} (${user.email}) - ${user.role}</li>
            </c:forEach>
        </ul>

        <br>
        <a href="${pageContext.request.contextPath}/logout">Logout</a>
    </div>
</body>
</html>