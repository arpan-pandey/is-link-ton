<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Threads - Islinkton</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/threads.css">
</head>
<body>
	<jsp:include page="/components/user-header.jsp" />
	
	<main class="threads-container">
		<div class="threads-button-container card">
			<a class="create-thread-button" href="${pageContext.request.contextPath}/threads/create">Create Thread</a>
		</div>
		<div class="threads-card-container">
			<c:forEach var="thread" items="${threads}">
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
			
			<c:if test="${empty threads}">
				<p class="no-data-msg">No recent academic threads have been posted yet.</p>
			</c:if>
		</div>
	</main>
	
	<jsp:include page="/components/footer.jsp" />
</body>
</html>