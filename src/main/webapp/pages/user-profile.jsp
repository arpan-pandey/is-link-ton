<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Account - Islinkton</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages/user-profile.css">
</head>
<body>

    <jsp:include page="/components/user-header.jsp" />

    <main>
        <div class="profile-header-centered">
            <h1 class="page-title">Manage Account</h1>
            <p class="page-subtitle">Update your profile details and security settings.</p>
        </div>

        <!-- Alert Messages -->
        <c:if test="${not empty message}">
            <div class="alert-banner-success">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert-banner-error">${error}</div>
        </c:if>

        <div class="account-forms-container">

            <!-- ==================== PROFILE INFORMATION ==================== -->
            <div class="profile-content-card">
                <h3 class="card-header-title">Profile Information</h3>

                <form action="${pageContext.request.contextPath}/profile" method="post" 
                      enctype="multipart/form-data">

                    <!-- Profile Picture -->
                    <div class="profile-avatar-row">
                        <div class="square-avatar-frame">
                            <img id="avatarImage"
                                 src="${pageContext.request.contextPath}/getimage?name=${user.username}"
                                 alt="Profile Picture"
                                 onerror="this.src='https://via.placeholder.com/90?text=User';">
                        </div>

                        <div class="avatar-actions-column">
                            <label for="fileSelector" class="btn-action-navy">Choose New Photo</label>
                            <input type="file" id="fileSelector" name="profileImage"
                                   accept="image/*" onchange="previewFile()" class="hidden-file-input">
                            <p class="helper-caption-text">Recommended: Square image (JPG, PNG) - Max 5MB</p>
                        </div>
                    </div>

                    <!-- Personal Information -->
                    <div class="form-row-split">
                        <div class="input-wrapper-block">
                            <label>First Name</label>
                            <input type="text" name="firstName"
                                   value="${fn:contains(user.fullName, ' ') ? fn:substringBefore(user.fullName, ' ') : user.fullName}" required>
                        </div>
                        <div class="input-wrapper-block">
                            <label>Last Name</label>
                            <input type="text" name="lastName"
                                   value="${fn:contains(user.fullName, ' ') ? fn:substringAfter(user.fullName, ' ') : ''}" required>
                        </div>
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

            <!-- ==================== CHANGE PASSWORD ==================== -->
            <div class="profile-content-card">
                <h3 class="card-header-title">Change Password</h3>

                <form action="${pageContext.request.contextPath}/profile" method="post">
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