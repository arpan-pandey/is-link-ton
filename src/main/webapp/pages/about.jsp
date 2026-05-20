<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>About Us - Islinkton</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/about.css">
</head>
<body>

    <div class="page-content">
        <main class="content-container">
            <div class="inner-block">
                <h1 class="page-main-title">About Islinkton</h1>
                <p class="platform-description">
                    Islinkton is a premier digital ecosystem built specifically to foster advanced academic collaboration and knowledge discourse. By breaking down organizational siloes, our platform empowers students to form fluid study groups, spark collective action via public petitions, share academic resources, and co-create an interconnected space dedicated to scholastic excellence.
                </p>

                <div class="divider-line"></div>

                <h2 class="section-title">Core Team Members</h2>
                <div class="team-grid">
                    <div class="team-card">
                        <div class="member-avatar-placeholder">AP</div>
                        <h3 class="member-name">Arpan Pandey</h3>
                    </div>
                    <div class="team-card">
                        <div class="member-avatar-placeholder">HG</div>
                        <h3 class="member-name">Hardik Ghimire</h3>
                    </div>
                    <div class="team-card">
                        <div class="member-avatar-placeholder">TM</div>
                        <h3 class="member-name">Trishala Maharjan</h3>
                    </div>
                    <div class="team-card">
                        <div class="member-avatar-placeholder">PS</div>
                        <h3 class="member-name">Purbesh Shrestha</h3>
                    </div>
                </div>

                <div class="back-home-box">
                    <a href="${pageContext.request.contextPath}/home" class="back-link">Back to Home</a>
                </div>
            </div>
        </main>
    </div>

</body>
</html>