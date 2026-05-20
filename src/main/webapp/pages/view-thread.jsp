<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><c:out value="${thread.title}" /> - Islinkton</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/threads.css">
</head>
<body>
	<jsp:include page="/components/user-header.jsp" />
	
	<main class="thread-container">
		
		<!-- basic breadcrumb navigator at the top of the main section (&gt; is ' > ')-->
		<nav class="breadcrumb-nav">
			<a href="${pageContext.request.contextPath}/threads">Threads</a> &gt; 
			<a href="${pageContext.request.contextPath}/threads?category=${thread.categoryId}">
				<c:out value="${thread.categoryName}" />
			</a> &gt; 
			<span class="active-crumb"><c:out value="${thread.title}" /></span>
		</nav>
		
		<div class="card thread-card view-card">
			<div class="thread-main view-main">
				<div class="thread-details view-thread-details">
					<span class="category-flair"><c:out value="${thread.categoryName}" /></span>
					<span class="thread-creator-time-info">Posted by @<c:out value="${thread.authorUserName}" /> &nbsp; <strong>(<c:out value="${thread.timeAgo}" />)</strong></span>
				</div>
				
				<h1 class="thread-title view-title">
					<c:out value="${thread.title}" />
				</h1>
				
				<span class="view-body"><c:out value="${thread.content}" /></span>
			</div>
			<div class="thread-interaction-container">
				<div>
					<a href="${pageContext.request.contextPath}/threads/vote?id=${thread.id}" class="thread-vote-button">🠅</a>
					<span class="vote-count"><c:out value="${thread.voteCount}" /> votes</span>
					<span class="interaction-divider">｜</span>
					<span>Comments: <c:out value="${thread.commentCount}" /></span>
				</div>
			</div>
		</div>
	</main>
	
	<jsp:include page="/components/footer.jsp" />
</body>
</html>