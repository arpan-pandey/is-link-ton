<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="fullName" value="${user.name}" />

<%-- cleaning name --%>
<c:set var="cleanName" value="${fn:trim(fn:replace(fullName, '  ', ' '))}" />

<%-- getting initials --%>
<c:set var="words" value="${fn:split(cleanName, ' ')}" />
<c:set var="initials" value="" />

<c:forEach var="word" items="${words}" varStatus="status">
    <c:if test="${not empty word}">
        <c:set var="initials" value="${initials}${fn:toUpperCase(fn:substring(word, 0, 1))}" />
    </c:if>
</c:forEach>

<c:if test="${fn:length(words) > 0}">
    <%-- First initial --%>
    <c:set var="initials" value="${fn:toUpperCase(fn:substring(words[0], 0, 1))}" />
    
    <%-- Add last initial if more than one word --%>
    <c:if test="${fn:length(words) > 1}">
        <c:set var="lastWord" value="${words[fn:length(words) - 1]}" />
        <c:set var="initials" value="${initials}${fn:toUpperCase(fn:substring(lastWord, 0, 1))}" />
    </c:if>
</c:if>

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
        		<div id="profile-icon"> ${initials} </div>
        		<div id="profile-details">
        			<span style="font-weight: bold;">${fullName}</span>
        			<span id="user-title">Student</span>
        		</div>
        	</div>
	</aside>
</html>