<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Dashboard - Islinkton</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/user-dashboard.css">
</head>
<body>
	<jsp:include page="/components/user-header.jsp" />
	
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
		
		<div class="recents">
			<div class="recents-titlebox">
				<h3>Recent Threads</h3>
				<a href="${pageContext.request.contextPath}/threads/">View All →</a>
			</div>
			<div class="threads-card-container">
				<c:forEach var="thread" items="${recentThreads}">
					<div class="card thread-card">
						<aside class="vote-thread-container">
							<div>
								<a href="${pageContext.request.contextPath}/threads/vote?id=${thread.id}" class="thread-vote-button">⮝</a>
								<span class="vote-count"><c:out value="${thread.voteCount}" /></span>
							</div>
						</aside>
						<div class="thread-main">
							<div class="thread-details">
								<div>
									<span class="category-flair"><c:out value="${thread.categoryName}" /></span>
									<span class="thread-creator-info">Posted by @<c:out value="${thread.authorUserName}" /></span>
								</div>
								<span class="thread-time-info"><c:out value="${thread.timeAgo}" /></span>
							</div>
							<div class="thread-content">
								<span class="thread-title">
									<a href="${pageContext.request.contextPath}/threads/view?id=${thread.id}">
										<c:out value="${thread.title}" />
									</a>
								</span>
								<span class="thread-body"><c:out value="${thread.content}" /></span>
							</div>
							<a href="${pageContext.request.contextPath}/threads/view?id=${thread.id}" class="comment-number center">
								<span>Comments: <c:out value="${thread.commentCount}" /></span>
							</a>
						</div>
					</div>
				</c:forEach>
				
				<c:if test="${empty recentThreads}">
					<p class="no-data-msg">No recent academic threads have been posted yet.</p>
				</c:if>
			</div>
		</div>
	
		<div class="recents">
			<div class="recents-titlebox">
				<h3>Recent Petitions</h3>
				<a href="${pageContext.request.contextPath}/petitions/">View All →</a>
			</div>
				<div class="petition-card-container">
					<div class="card petition-card">
						<div class="petition-main">
							<div class="petition-details">
								<span class="category-flair">Academic Policy</span>
								<span class="petition-time-info">21 days ago</span>
							</div>
							<div class="petition-content">
								<span class="petition-title">
									Extend the library hours during midterms
								</span>
								<span class="petition-body">The current 10 PM closure of the central library restricts study time during peak assessment periods. This petition requests extending hours to 2 AM during</span>
								<span class="petition-votes">Votes: 67</span>	
							</div>
						</div>
						<div class="petition-interaction">
							<span class="petition-creator-info">By: @Student</span>
							<a class="petition-vote-button" href="${pageContext.request.contextPath}/petitions/vote?id=${petition.id}">Vote</a>							
						</div>
					</div>
				</div>
			</div>
	</main>
	
	<jsp:include page="/components/footer.jsp" />
</body>
</html>