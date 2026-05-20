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

        <c:if test="${not empty message}">
            <div class="alert-banner-success">${message}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert-banner-error">${error}</div>
        </c:if>

        <div class="account-forms-container">
            
            <div class="profile-content-card">
                <h3 class="card-header-title">Profile Picture</h3>
                
                <div class="profile-avatar-row">
                    <div class="square-avatar-frame">
                        <img src="${pageContext.request.contextPath}/getimage?name=${user.username}" 
                             alt="Profile Avatar"
                             onerror="this.src='https://via.placeholder.com/100?text=User';">
                    </div>
                    
                    <div class="avatar-actions-column">
                        <form action="profile" method="post" enctype="multipart/form-data">
                            <div class="avatar-buttons-wrapper">
                                <label for="fileSelector" class="btn-action-navy">Upload New</label>
                                <input type="file" id="fileSelector" name="profileImage" accept="image/*" class="hidden-file-input">
                                <button type="button" class="btn-action-outline">Remove</button>
                            </div>
                        </form>
                        <p class="helper-caption-text">Recommended size: 256×256px. Max file size: 2MB.</p>
                    </div>
                </div>
            </div>

            <div class="profile-content-card">
                <h3 class="card-header-title">Personal Information</h3>
                
                <form action="profile" method="post">
                    <div class="form-row-split">
                        <div class="input-wrapper-block">
                            <label>First Name</label>
                            <input type="text" name="firstName" value="${fn:contains(user.fullName, ' ') ? fn:substringBefore(user.fullName, ' ') : user.fullName}">
                        </div>
                        <div class="input-wrapper-block">
                            <label>Last Name</label>
                            <input type="text" name="lastName" value="${fn:contains(user.fullName, ' ') ? fn:substringAfter(user.fullName, ' ') : ''}">
                        </div>
                    </div>

                    <div class="input-wrapper-block">
                        <label>University Email</label>
                        <input type="email" value="${user.email}" readonly class="readonly-input">
                        <p class="helper-caption-text">University email cannot be changed.</p>
                    </div>

                    <div class="input-wrapper-block">
                        <label>Major / Department</label>
                        <input type="text" name="department" value="">
                    </div>

                    <div class="input-wrapper-block">
                        <label>Short Bio</label>
                        <textarea name="bio" rows="4"></textarea>
                    </div>

                    <div class="form-actions-right">
                        <button type="submit" class="btn-action-navy-submit">Save Changes</button>
                    </div>
                </form>
            </div>

            <div class="profile-content-card">
                <h3 class="card-header-title">Change Password</h3>
                
                <form action="update-password" method="post">
                    <div class="input-wrapper-block">
                        <label>Current Password</label>
                        <input type="password" name="currentPassword">
                    </div>
                    
                    <div class="input-wrapper-block">
                        <label>New Password</label>
                        <input type="password" name="newPassword">
                    </div>
                    
                    <div class="input-wrapper-block">
                        <label>Confirm New Password</label>
                        <input type="password" name="confirmPassword">
                    </div>

                    <div class="form-actions-left">
                        <button type="submit" class="btn-action-outline-submit">Update Password</button>
                    </div>
                </form>
            </div>

        </div>
    </main>

    <jsp:include page="/components/footer.jsp" />

</body>
</html>