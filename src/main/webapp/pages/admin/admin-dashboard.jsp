<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>   
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard - Islinkton</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/user-dashboard.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/dashboard.css">
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
    
    <main class="dashboard-main">
        <div class="page-titlebox">
            <c:if test="${not empty sessionScope.user}">
                <c:set var="nameParts" value="${fn:split(sessionScope.user.fullName, ' ')}" />
                <h1 class="page-title">Welcome to Islinkton, <c:out value="${nameParts[0]}" />!</h1>
            </c:if>
            <span class="page-subtitle">
                System Administration Hub. Manage users, monitor discussions, and review student actions.
            </span>
        </div>

        <div class="dashboard-grid">
            <div class="stat-card">
                <h3>Total Accounts</h3>
                <div class="stat-value"><c:out value="${fn:length(allUsers)}" /></div>
            </div>
            <div class="stat-card">
                <h3>Pending Petitions</h3>
                <div class="stat-value"><c:out value="${fn:length(pendingPetitions)}" /></div>
            </div>
            <div class="stat-card">
                <h3>Tracked Threads</h3>
                <div class="stat-value"><c:out value="${fn:length(allThreads)}" /></div>
            </div>
        </div>

        <div class="management-section">
            <div class="section-header">
                <h2>User Management Matrix</h2>
            </div>
            <table class="admin-table">
                <thead>
                    <tr>
                        <th>User ID</th>
                        <th>Username</th>
                        <th>Email Address</th>
                        <th>Assigned Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="u" items="${allUsers}">
                        <tr>
                            <td>#<c:out value="${u.id}"/></td>
                            <td><strong><c:out value="${u.username}"/></strong></td>
                            <td><c:out value="${u.email}"/></td>
                            <td><span class="badge badge-primary"><c:out value="${u.role}"/></span></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/dashboard" method="POST" class="inline-form" onsubmit="return confirm('Revoke account and delete user permanently?');">
                                    <input type="hidden" name="targetType" value="user">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${u.id}">
                                    <button type="submit" class="action-btn btn-reject">Revoke Account</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty allUsers}">
                        <tr>
                            <td colspan="5" style="text-align: center; color: #94a3b8; padding: 20px;">No registered user metrics found inside active schema scope.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <div class="management-section">
            <div class="section-header">
                <h2>Pending Petition Verification Queue</h2>
            </div>
            <table class="admin-table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Petition Title</th>
                        <th>Proposed By</th>
                        <th>Status</th>
                        <th>Action Processing</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="petition" items="${pendingPetitions}">
                        <tr>
                            <td>#<c:out value="${petition.id}"/></td>
                            <td><strong><c:out value="${petition.title}"/></strong></td>
                            
                            <%-- 🚀 FIXED: Changed from authorName to authorUserName to resolve EL exception --%>
                            <td><c:out value="${petition.creatorUserName}"/></td>
                            
                            <td><span class="badge badge-warning">Pending Review</span></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/dashboard" method="POST" class="inline-form">
                                    <input type="hidden" name="targetType" value="petition">
                                    <input type="hidden" name="action" value="approve">
                                    <input type="hidden" name="id" value="${petition.id}">
                                    <button type="submit" class="action-btn btn-approve">Approve</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/admin/dashboard" method="POST" class="inline-form" onsubmit="return confirm('Reject and drop this petition submission?');">
                                    <input type="hidden" name="targetType" value="petition">
                                    <input type="hidden" name="action" value="reject">
                                    <input type="hidden" name="id" value="${petition.id}">
                                    <button type="submit" class="action-btn btn-reject">Reject</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty pendingPetitions}">
                        <tr>
                            <td colspan="5" style="text-align: center; color: #94a3b8; padding: 20px;">Verification clean: No petitions currently pending approval.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>

        <div class="management-section">
            <div class="section-header">
                <h2>Discussion Thread Auditing</h2>
            </div>
            <table class="admin-table">
                <thead>
                    <tr>
                        <th>Thread ID</th>
                        <th>Topic Title</th>
                        <th>Creator</th>
                        <th>Category</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="thread" items="${allThreads}">
                        <tr>
                            <td>#<c:out value="${thread.id}"/></td>
                            <td><strong><c:out value="${thread.title}"/></strong></td>
                            <td><c:out value="${thread.authorUserName}"/></td>
                            <td><span class="badge badge-success"><c:out value="${thread.categoryName}"/></span></td>
                            <td>
                                <form action="${pageContext.request.contextPath}/admin/dashboard" method="POST" class="inline-form" onsubmit="return confirm('Purge thread structure and all associated commentary nodes?');">
                                    <input type="hidden" name="targetType" value="thread">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="${thread.id}">
                                    <button type="submit" class="action-btn btn-reject">Purge Node</button>
                                </form>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty allThreads}">
                        <tr>
                            <td colspan="5" style="text-align: center; color: #94a3b8; padding: 20px;">No live active discourse threads present inside data cluster.</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </main>
    
    <jsp:include page="/components/footer.jsp" />
</body>
</html>