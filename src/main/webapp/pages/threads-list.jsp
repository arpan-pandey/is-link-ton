<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Threads - Islinkton</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/create-form.css">
</head>
<body>
	<jsp:include page="/components/user-header.jsp" />
	
	<main class="creation-container">
		<div class="creation-button-container">
			<div>
			<h1 class="page-title">Threads</h1>
				<span class="page-subtitle">
					Academic Discourse, Campus News & Announcements
				</span>
			</div>
			<div>
				<a class="creation-button" href="${pageContext.request.contextPath}/threads/create">Create Thread</a>
			</div>
		</div>
		<div class="threads-card-container">
		    <c:forEach var="thread" items="${threads}">
		        <%-- Check tracking arrays --%>
		        <c:set var="hasVotedThread" value="${not empty votedThreadIds && fn:contains(votedThreadIds, thread.id)}" />
		
		        <div class="card thread-card">
		            <aside class="vote-thread-container">
		                <div>
		                    <form action="${pageContext.request.contextPath}/vote/thread" method="POST" style="margin: 0; display: inline;">
					            <input type="hidden" name="id" value="${thread.id}" />
					            <button type="submit" class="thread-vote-button ${hasVotedThread ? 'active-voted' : ''}">⮝</button>
					        </form>
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
		</div>
	</main>
	
	<jsp:include page="/components/footer.jsp" />
</body>
</html>