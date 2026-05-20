<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    
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
					<span class="thread-creator-time-info">Posted by u&sol;<c:out value="${thread.authorUserName}" /> &nbsp; <strong>(<c:out value="${thread.timeAgo}" />)</strong></span>
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
		
		<div class="comment-section-container card thread-card">
			<div class="comments-header">
				<div>
				    <h3>Discussion</h3>
					
					<button onclick="document.getElementById('create-comment').style.display='block';">
						Add a Comment
					</button>	
				</div>	
                      
				<!-- Add comment form -->
		    	<div id="create-comment" class="reply-drawer-form" style="display: none;">
                   <form action="${pageContext.request.contextPath}/posts" method="post" class="reply-form">
						<input type="hidden" name="action" value="create">
						<input type="hidden" name="threadId" value="${thread.id}">
						
						<textarea name="content" rows="4" class="reply-textarea" placeholder="Write a comment..." style="width: 100%;" required></textarea>
						<div class="reply-form-buttons">
						    <button type="button" class="cancel-reply-btn" onclick="document.getElementById('create-comment').style.display='none';">Cancel</button>
						    <button type="submit" class="submit-reply-btn">Submit</button>
						</div>
                   </form>
                </div>	
                
			</div>
		    
		    <div class="comments-container">
		    
			    <c:choose>
			        <c:when test="${not empty posts}">
			        
			        	<!-- COMMENTS -->
			            <c:forEach var="post" items="${posts}" varStatus="status">
							<div class="comment">
								<div>
									<!-- profile image  --> 
									<img src="${pageContext.request.contextPath}/getfile?path=images/${post.profileImage}"
				                     alt="Profile"
				                     class="avatar comment-avatar"
				                     onerror="this.src='${pageContext.request.contextPath}/assets/images/default-avatar.jpg';">
				                     
				                     <div class="comment-main">
				                     	<div class="comment-main-header">
				                     		<c:choose>
											    <%-- THREAD CREATOR (OP) --%>
											    <c:when test="${post.userId == thread.authorId}">
											        <span class="category-flair op comment-flair">OP</span>
											    </c:when>
											
											    <%-- ADMIN OR TEACHER --%>
											    <c:when test="${post.userRole == 'Admin' || post.userRole == 'Faculty'}">
											        <span class="category-flair verified comment-flair"><c:out value="${post.userRole}" /></span>
											    </c:when>
											    
											    <%-- THREAD CREATOR (OP) --%>
											    <c:otherwise>
											        <span class="category-flair normal comment-flair">Student</span>
											    </c:otherwise>
											</c:choose>
											
				                     		<strong>u/<c:out value="${post.authorUserName}" /></strong>
											<span>(<c:out value="${post.timeAgo}" />)</span>
											
											<!-- checking if user logged in currently created the comment and displaying option to delete it -->
											<c:if test="${sessionScope.user.id == post.userId}">
											
												<!-- inline form (delete button only, but calls the DAO method) -->
					                            <form action="${pageContext.request.contextPath}/posts" method="post" 
					                            	onsubmit="return confirm('Delete this comment and all its nested replies?');" class="inline-form">
					                                
					                                <input type="hidden" name="action" value="delete">
					                                <input type="hidden" name="threadId" value="${post.threadId}">
					                                <input type="hidden" name="postId" value="${post.id}">
					                                <button type="submit" class="delete-comment-btn">Remove</button>
					                            </form>
					                            
					                        </c:if>										
				                     	</div>
				                     	
				                     	<span class="comment-content"><c:out value="${post.content}" /></span>
				                     </div>
								</div>
								<section>
				                     <button type="button" class="reply-toggle-btn" onclick="document.getElementById('box-${post.id}').style.display='block';">
			                            Reply
			                        </button>
				                     
									<div id="box-${post.id}" class="reply-drawer-form" style="display: none;">
				                        <form action="${pageContext.request.contextPath}/posts" method="post" class="reply-form">
				                            <input type="hidden" name="action" value="create">
				                            <input type="hidden" name="threadId" value="${post.threadId}">
				                            <input type="hidden" name="parentPostId" value="${post.id}">
				                            
				                            <textarea name="content" rows="4" class="reply-textarea" placeholder="Write a public reply..." required></textarea>
				                            <div class="reply-form-buttons">
				                                <button type="button" class="cancel-reply-btn" onclick="document.getElementById('box-${post.id}').style.display='none';">Cancel</button>
				                                <button type="submit" class="submit-reply-btn">Submit</button>
				                            </div>
				                        </form>
				                    </div>
			                    </section>
							</div>
							
							
							<!-- REPLIES -->
							<c:if test="${not empty post.replies}">
		                     	<c:forEach var="reply" items="${post.replies}">
		                     		<div class="comment" style="border-left: 2px solid #c4c6cd; margin-left: 54px; padding-left: 1.5rem; padding-top: 10px;">
			                     		<div>
											<!-- profile image  --> 
											<img src="${pageContext.request.contextPath}/getfile?path=images/${reply.profileImage}"
						                     alt="Profile"
						                     class="avatar comment-avatar"
						                     onerror="this.src='${pageContext.request.contextPath}/assets/images/default-avatar.jpg';">
						                     
						                     <div class="comment-main">
						                     		
						                     	<div class="comment-main-header">
						                     		<c:choose>
													    <%-- THREAD CREATOR (OP) --%>
													    <c:when test="${reply.userId == thread.authorId}">
													        <span class="category-flair op comment-flair">OP</span>
													    </c:when>
													
													    <%-- ADMIN OR TEACHER --%>
													    <c:when test="${reply.userRole == 'Admin' || reply.userRole == 'Faculty'}">
													        <span class="category-flair verified comment-flair"><c:out value="${reply.userRole}" /></span>
													    </c:when>
													    
													    <%-- THREAD CREATOR (OP) --%>
													    <c:otherwise>
													        <span class="category-flair normal comment-flair">Student</span>
													    </c:otherwise>
													</c:choose>
													
						                     		<strong>u/<c:out value="${reply.authorUserName}" /></strong>
													<span>(<c:out value="${reply.timeAgo}" />)</span>
													
													<!-- checking if user logged in currently created the comment and displaying option to delete it -->
													<c:if test="${sessionScope.user.id == reply.userId}">
													
														<!-- inline form (delete button only, but calls the DAO method) -->
							                            <form action="${pageContext.request.contextPath}/posts" method="post" 
							                            	onsubmit="return confirm('Delete this reply?');" class="inline-form">
							                                
							                                <input type="hidden" name="action" value="delete">
							                                <input type="hidden" name="threadId" value="${reply.threadId}">
							                                <input type="hidden" name="postId" value="${reply.id}">
							                                <button type="submit" class="delete-comment-btn">Remove</button>
							                            </form>
							                            
							                        </c:if>										
						                     	</div>
						                     	
						                     	<span class="comment-content"><c:out value="${reply.content}" /></span>
						                     </div>
					                     </div>
					            	</div>
		                     	</c:forEach>
							</c:if>
							<c:if test="${not status.last}"><hr style="border: 0.5px solid #ccc; margin: 0.5rem 0;"></c:if>
			            </c:forEach>
			        </c:when>
			        <c:otherwise>
			            <p class="no-comments-fallback">No comments posted yet. Start the conversation!</p>
			        </c:otherwise>
			    </c:choose>
		    </div>
		</div>
	</main>
	
	<jsp:include page="/components/footer.jsp" />
</body>
</html>