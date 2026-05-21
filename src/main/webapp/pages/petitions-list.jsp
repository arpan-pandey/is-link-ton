<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Petitions - Islinkton</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/create-form.css">
</head>
<body>
	<jsp:include page="/components/user-header.jsp" />
	
		<c:if test="${not empty sessionScope.message}">
		    <div id="successBox" class="popup-message success-toast">
		        <c:out value="${sessionScope.message}"/>
		    </div>
		    <c:remove var="message" scope="session" />
		</c:if>
		
		<c:if test="${not empty sessionScope.error}">
		    <div id="errorBox" class="popup-message">
		        <c:out value="${sessionScope.error}"/>
		    </div>
		    <c:remove var="error" scope="session" />
		</c:if>
	
	<main class="creation-container">
		<div class="creation-button-container">
			<div>
			<h1 class="creation-page-title">Active Petitions</h1>
				<span class="page-subtitle">
					Review, support, and track student-led initiatives aimed at improving campus life
					<br />and academic policies at Islinkton University. 
				</span>
			</div>
			<div>
				<c:if test="${sessionScope.user.role eq 'Student'}">	                        
					<a class="creation-button" href="${pageContext.request.contextPath}/petitions/create">Create Petition</a>
                </c:if>
			</div>
		</div>
		
		<div class="petition-card-container">
		    <c:forEach var="petition" items="${petitions}">
		        <c:set var="hasVotedPetition" value="${not empty votedPetitionIds && fn:contains(votedPetitionIds, petition.id)}" />
		
		        <div class="card petition-card">
		            <div class="petition-main">
		                <div class="petition-details">
		                    <span class="category-flair"><c:out value="${petition.categoryName}"/></span>
		                    <span class="petition-time-info"><c:out value="${petition.timeAgo}" /></span>
		                </div>
		                <div class="petition-content">
		                    <span class="petition-title">
		                            <c:out value="${petition.title}" />
		                    </span>
		                    <span class="petition-body"><c:out value="${petition.content}" /></span>
		                    <span class="petition-votes">Votes: <c:out value="${petition.voteCount}" /></span>    
		                </div>
		            </div>
		            <div class="petition-interaction">
		                <span class="petition-creator-info">By: @<c:out value="${petition.creatorUserName}" /></span>
		                
		                <c:choose>
		                    <c:when test="${sessionScope.user.role eq 'Admin'}">
		                        <form action="${pageContext.request.contextPath}/petitions/delete" method="post" 
		                        	onsubmit="return confirm('Delete this petition?');" class="inline-form">
		                            <input type="hidden" name="targetType" value="petition">
		                            <input type="hidden" name="action" value="reject">
		                            <input type="hidden" name="id" value="${petition.id}">
		                            <button type="submit" class="delete-item-btn">Remove</button>
		                        </form>
		                    </c:when>
		                    <c:otherwise>
		                        <form action="${pageContext.request.contextPath}/vote/petition" method="POST" style="margin: 0;">
		                            <input type="hidden" name="id" value="${petition.id}" />
		                            <button type="submit" class="petition-vote-button ${hasVotedPetition ? 'voted-state' : ''}">
		                                <c:choose>
		                                    <c:when test="${hasVotedPetition}">Voted</c:when>
		                                    <c:otherwise>Vote</c:otherwise>
		                                </c:choose>
		                            </button>
		                        </form>
		                    </c:otherwise>
		                </c:choose>
		            </div>
		        </div>
		    </c:forEach>
		
		    <c:if test="${empty petitions}">
		        <p class="no-data-msg">No active student petitions found.</p>
		    </c:if>
		</div>
	</main>
	
	<jsp:include page="/components/footer.jsp" />
</body>
</html>