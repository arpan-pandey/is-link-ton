<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="fullName" value="${user.name}" />

<%-- cleaning name --%>
<c:set var="cleanName" value="${fn:trim(fn:replace(fullName, '  ', ' '))}" />

<%-- getting firstname --%>
<c:set var="firstName" value="${fn:split(cleanName, ' ')[0]}" />

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Admin Dashboard - Islinkton</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
		
		<style>
		
			body{
				background: #4A1E1E;
			}
		
			#sidebar{
				background-color: #4A1E1E;
				
				&>nav{
					border-top: 0.5px solid #6B2A2A;
					border-bottom: 0.5px solid #6B2A2A;
					
					&>a{
						&:hover{
							background: hsla(0, 45%, 30%, 0.5);
						}
					}
				}
			}
			
			#profile-icon{
				background: #5A2A2A;
			}
			
			#welcome-text{
				color: #7E3A37;
			}
			
		</style>
		
	</head>
	<body>
		<jsp:include page="../../components/admin-header.jsp"></jsp:include>
		<main>
			<header>
				<h1 id="page-title">Admin Dashboard</h1>
				<span id="welcome-text">Welcome back, ${firstName}!</span>
			</header>
			<section class="content-card">
    <h2>Manage Users</h2>
    
    <!-- response handling -->
	<c:if test="${param.success != null || param.error != null}">
	    <div id="alertMessage" 
	         style="transition: opacity 0.5s ease; position: absolute; bottom: 0; left:50%; padding: 12px 20px; margin-bottom: 2rem; border-radius: 30px; font-weight: bold; 
	                ${param.success != null ? 'background:#d4edda; color:#155724;' : 'background:#f8d7da; color:#721c24;'}">
	         ${param.success != null ? param.success : param.error}
	    </div>

	    <script>
	        // auto hide after 5 seconds
	        setTimeout(function() {
	            var msg = document.getElementById("alertMessage");
	            if (msg) {
	                msg.style.transition = "opacity 0.5s";
	                msg.style.opacity = "0";
	                setTimeout(function() {
	                    msg.style.display = "none";
	                }, 500);
	            }
	        }, 5000); // 5000ms = 5 seconds
	    </script>
	</c:if>

    <!-- add new user form -->
    <div id="add-user-box">
        <h4>Add New User</h4>
        <form action="${pageContext.request.contextPath}/addUser" method="post">
            <input type="text" name="name" placeholder="Full Name" required style="margin:5px;">
            <input type="email" name="email" placeholder="Email Address" required style="margin:5px;">
            <input type="password" name="password" placeholder="Password" required style="margin:5px;">
            
            <select name="role" required style="margin:5px;">
                <option value="student">Student</option>
                <option value="admin">Admin</option>
            </select>
            
            <button type="submit">Add User</button>
        </form>
        
    </div>

    <c:forEach var="user" items="${allUsers}">
        <div class="announcement-container">
            <div class="announcement-info">
                <span class="announcement-title">${user.name}</span>
                <span class="announcement-date">${user.email}</span>
            </div>
            
            <span class="container-tag">${user.role == 'student' ? 'Student' : 'Admin'}</span>
            
            <form action="${pageContext.request.contextPath}/updateUser" method="post" style="display:inline;">
                <input type="hidden" name="userID" value="${user.id}">
                <input type="text" name="name" value="${user.name}" size="12" style="margin:0 5px;">
                <input type="email" name="email" value="${user.email}" size="20" style="margin:0 5px;">
                
                <select name="role" style="margin:0 5px;">
                    <option value="student" ${user.role == 'student' ? 'selected' : ''}>Student</option>
                    <option value="admin" ${user.role == 'admin' ? 'selected' : ''}>Admin</option>
                </select>
                
                <button type="submit" style="margin:0 5px;">Update</button>
            </form>
            
            <form action="${pageContext.request.contextPath}/deleteUser" method="post" style="display:inline;" 
                  onsubmit="return confirm('Delete ${user.name}?')">
                <input type="hidden" name="email" value="${user.email}">
                <span class="container-tag" style="color:red; cursor:pointer; font-size: 1rem; border: 1px solid #AA6666; border-radius: 12px;" onclick="this.closest('form').submit();">⨯</span>
            </form>
        </div>
    </c:forEach>
</section>
		</main>
	</body>
</html>