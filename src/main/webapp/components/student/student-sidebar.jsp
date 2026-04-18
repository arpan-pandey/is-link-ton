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
        		<a href="#" style="background: #364159;">
        		<img src="${pageContext.request.contextPath}/assets/icons/nav-studentdashboard.png" class="nav-icon" />
        		Dashboard
 	      		</a>
        		<a href="#">
        		<img src="${pageContext.request.contextPath}/assets/icons/nav-threads.png" class="nav-icon"/>
        		Threads
        		</a>
        		<a href="#">
        		<img src="${pageContext.request.contextPath}/assets/icons/nav-petitions.png" class="nav-icon"/>
        		Petitions
        		</a>
        		<a href="#">
        		<img src="${pageContext.request.contextPath}/assets/icons/nav-studygroups.png" class="nav-icon" />
        		Study Groups
        		</a>
        		<a href="#">
        		<img src="${pageContext.request.contextPath}/assets/icons/nav-resources.png" class="nav-icon"/>
        		Resources
        		</a>
        	</nav>
        	<div id="user-info">
        		<div id="profile-icon">JD</div>
        		<div id="profile-details">
        			<span style="font-weight: bold;">Jenish Dhital</span>
        			<span id="user-title">Student</span>
        		</div>
        	</div>
	</aside>
</html>