<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>   
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
        
        <%-- SECTION 1: USER MANAGEMENT --%>
        <div class="card management-section">
            <div class="section-header">
                <h2 class="section-title">User Account Registry Matrix</h2>
            </div>
            
            <div class="table-container">
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>User ID</th>
                            <th>Username</th>
                            <th>Email Address</th>
                            <th>System Role</th>
                            <th>Approval Status</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="u" items="${allUsers}">
                            <tr>
                                <td>#<c:out value="${u.id}"/></td>
                                <td><strong class="text-highlight"><c:out value="${u.username}"/></strong></td>
                                <td><c:out value="${u.email}"/></td>
                                <td><span class="badge badge-primary"><c:out value="${u.role}"/></span></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${u.approved}">
                                            <span class="badge badge-success">Approved</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-warning">Pending Approval</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="action-button-group">
                                        <c:choose>
                                            <c:when test="${not u.approved}">
                                                <form action="${pageContext.request.contextPath}/admin/dashboard" method="POST" class="inline-form">
                                                    <input type="hidden" name="targetType" value="user">
                                                    <input type="hidden" name="action" value="approve">
                                                    <input type="hidden" name="id" value="${u.id}">
                                                    <button type="submit" class="action-btn btn-approve">Approve</button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form action="${pageContext.request.contextPath}/admin/dashboard" method="POST" class="inline-form" 
                                                      onsubmit="return confirm('Purge user records permanently? This cannot be undone.');">
                                                    <input type="hidden" name="targetType" value="user">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="id" value="${u.id}">
                                                    <button type="submit" class="action-btn btn-reject">Delete User</button>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty allUsers}">
                            <tr>
                                <td colspan="6" class="empty-table-notice">No registered user metrics found inside active schema scope.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>

        <%-- SECTION 2: PETITION MANAGEMENT --%>
        <div class="card management-section">
            <div class="section-header">
                <h2 class="section-title">Student Petitions Management Registry</h2>
            </div>
            
            <div class="table-container">
                <table class="admin-table">
                    <thead>
                        <tr>
                            <th>Petition ID</th>
                            <th>Title</th>
                            <th>Category</th>
                            <th>Author</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${allPetitions}">
                            <tr>
                                <td>#<c:out value="${p.id}"/></td>
                                <td><strong class="text-highlight"><c:out value="${p.title}"/></strong></td>
                                <td><c:out value="${p.categoryName}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty p.creatorUserName}">
                                            <c:out value="${p.creatorUserName}"/>
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="action-button-group">
                                        <c:choose>
                                            <c:when test="${not p.isApproved}">
                                                <form action="${pageContext.request.contextPath}/admin/dashboard" method="POST" class="inline-form">
                                                    <input type="hidden" name="targetType" value="petition">
                                                    <input type="hidden" name="action" value="approve">
                                                    <input type="hidden" name="id" value="${p.id}">
                                                    <button type="submit" class="action-btn btn-approve">Approve</button>
                                                </form>
                                            </c:when>
                                            <c:otherwise>
                                                <form action="${pageContext.request.contextPath}/admin/dashboard" method="POST" class="inline-form" 
                                                      onsubmit="return confirm('Purge petition records permanently? This cannot be undone.');">
                                                    <input type="hidden" name="targetType" value="petition">
                                                    <input type="hidden" name="action" value="delete">
                                                    <input type="hidden" name="id" value="${p.id}">
                                                    <button type="submit" class="action-btn btn-reject">Delete Petition</button>
                                                </form>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        <c:if test="${empty allUsers}">
                            <tr>
                                <td colspan="6" class="empty-table-notice">No registered user metrics found inside active schema scope.</td>
                            </tr>
                        </c:if>
                    </tbody>
                </table>
            </div>
        </div>
        </div>

    </main>
    
    <jsp:include page="/components/footer.jsp" />
</body>
</html>