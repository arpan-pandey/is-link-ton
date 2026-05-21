<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Account - Islinkton</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/user-profile.css">
</head>
<body>

    <jsp:include page="/components/user-header.jsp" />
    
    <%-- Success Message Notification --%>
    <c:if test="${not empty sessionScope.message}">
        <div id="successBox" class="popup-message success-toast">
            <c:out value="${sessionScope.message}"/>
        </div>
        <c:remove var="message" scope="session" />
    </c:if>
		
    <%-- Error Message Notification --%>
    <c:if test="${not empty sessionScope.error}">
        <div id="errorBox" class="popup-message">
            <c:out value="${sessionScope.error}"/>
        </div>
        <c:remove var="error" scope="session" />
    </c:if>

    <main>
        <div class="profile-header-centered">
            <h1 class="page-title">Manage Account</h1>
            <p class="page-subtitle">Update your profile details and security settings.</p>
        </div>

        <div class="account-forms-container">

            <div class="profile-content-card">
                <h3 class="card-header-title">Profile Information</h3>

                <form action="${pageContext.request.contextPath}/profile/" method="post" 
                      enctype="multipart/form-data">
                    <input type="hidden" name="action" value="updateProfile">

                    <div class="profile-avatar-row">
                        <div class="square-avatar-frame">
                            <c:choose>
							    <c:when test="${not empty user.profileImage}">
							        <img id="avatarImage" 
							             src="${pageContext.request.contextPath}/getfile?path=images/${user.profileImage}"
							             alt="Profile Picture"
							             onerror="this.src='${pageContext.request.contextPath}/assets/images/default-avatar.jpg';">
							    </c:when>
							    <c:otherwise>
							        <img id="avatarImage" 
							             src="${pageContext.request.contextPath}/assets/images/default-avatar.jpg"
							             alt="Profile Picture">
							    </c:otherwise>
							</c:choose>
                        </div>

                        <div class="avatar-actions-column">
                            <label for="fileSelector" class="btn-action-navy">Choose New Photo</label>
                            <input type="file" id="fileSelector" name="profileImage" 
                                   accept="image/*" onchange="previewFile()" class="hidden-file-input">
                            <p class="helper-caption-text">Recommended: Square image (JPG, PNG) - Max 50MB</p>
                        </div>
                    </div>

                    <div class="input-wrapper-block">
                        <label>Full Name</label>
                        <input type="text" name="fullName" value="${user.fullName}" required>
                    </div>

                    <div class="input-wrapper-block">
                        <label>University Email</label>
                        <input type="email" name="email" value="${user.email}" required>
                    </div>

                    <div class="form-actions-right">
                        <button type="submit" class="btn-action-navy-submit">Save Changes</button>
                    </div>
                </form>
            </div>

            <div class="profile-content-card">
                <h3 class="card-header-title">Change Password</h3>

                <form action="${pageContext.request.contextPath}/profile/" method="post">
                    <input type="hidden" name="action" value="changePassword">

                    <div class="input-wrapper-block">
                        <label>Current Password</label>
                        <input type="password" name="currentPassword" required>
                    </div>
                    <div class="input-wrapper-block">
                        <label>New Password</label>
                        <input type="password" name="newPassword" required>
                    </div>
                    <div class="input-wrapper-block">
                        <label>Confirm New Password</label>
                        <input type="password" name="confirmPassword" required>
                    </div>

                    <div class="form-actions-right">
                        <button type="submit" class="btn-action-navy-submit">Update Password</button>
                    </div>
                </form>
            </div>

            <c:if test="${sessionScope.user.role eq 'Student'}">
                <div class="profile-content-card" style="border-top: 4px solid #ef4444;">
                    <h3 class="card-header-title" style="color: #ef4444;">Danger Zone</h3>
                    <p style="font-size: 14px; color: #666; margin-bottom: 1.5rem;">
                        Deactivating your account will disable your access instantly and mark your profile as inactive. 
                        You will not be able to log back into this platform.
                    </p>
                    <form action="${pageContext.request.contextPath}/profile/deactivate" method="post" 
                          onsubmit="return confirm('WARNING: Are you absolutely sure you want to deactivate your account? This action cannot be undone and you will be logged out immediately.');">
                        <div class="form-actions-right">
                            <button type="submit" class="btn-action-navy-submit" style="background-color: #ef4444; border-color: #ef4444;">
                                Deactivate Account
                            </button>
                        </div>
                    </form>
                </div>
            </c:if>

        </div>
    </main>

    <jsp:include page="/components/footer.jsp" />

    <script>
        function previewFile() {
            const file = document.getElementById('fileSelector').files[0];
            const avatarImage = document.getElementById('avatarImage');
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    avatarImage.src = e.target.result;
                };
                reader.readAsDataURL(file);
            }
        }
    </script>
</body>
</html>