<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<link rel="stylesheet" href="../css/styles.css">
	</head>
	<aside id="sidebar">
        	<a href="#">Islinkton</a>
        	<nav>
        		<a href="#" style="background: #7E3A37;">
        		<img src="${pageContext.request.contextPath}/assets/icons/nav-admindashboard.png" class="nav-icon" />
        		Admin Dashboard
 	      		</a>
        		<a href="#">
        		<img src="${pageContext.request.contextPath}/assets/icons/nav-threads.png" class="nav-icon"/>
        		Manage Threads
        		</a>
        		<a href="#">
        		<img src="${pageContext.request.contextPath}/assets/icons/nav-petitions.png" class="nav-icon"/>
        		Manage Petitions
        		</a>
        		<a href="#">
        		<img src="${pageContext.request.contextPath}/assets/icons/nav-studygroups.png" class="nav-icon" />
        		Manage Study Groups
        		</a>
        		<a href="#">
        		<img src="${pageContext.request.contextPath}/assets/icons/nav-resources.png" class="nav-icon" />
        		Manage Resources
        		</a>
        	</nav>
        	<div id="user-info">
        		<div id="profile-icon">JS</div>
        		<div id="profile-details">
        			<span style="font-weight: bold;">Jane Smith</span>
        			<span id="user-title">Admin</span>
        		</div>
        	</div>
	</aside>
</html>