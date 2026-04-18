<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Dashboard - Islinkton</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
		
		<style>
		
			body{
				background: #283348;
			}
		
			#sidebar{
				background-color: #283348;
				
				&>nav{
					border-top: 0.5px solid #3D475C;
					border-bottom: 0.5px solid #3D475C;
					
					&>a{
						&:hover{
							background: hsla(220, 25%, 28%, 0.5);
						}
					}
				}
			}
			
			#profile-icon{
				background: #364159;
			}
			
			#welcome-text{
				color: #364159;
			}
			
		</style>
		
	</head>
	<body>
		<jsp:include page="../../components/student/student-sidebar.jsp"></jsp:include>
		<main>
			<header>
				<h1 id="page-title">Dashboard</h1>
				<span id="welcome-text">Welcome back, Jenish.</span>
			</header>
			<section class="content-card">
				<h2>Announcements</h2>
				<div class="announcement-container">
					<div class="announcement-info">
						<span class="announcement-title">Library hours extended for finals week</span>
						<span class="announcement-date">Mar 25, 2026</span>
					</div>
					<span class="container-tag">Announcement</span>
				</div>
				<div class="announcement-container" style="border-top: 0.5px solid #A4A199; padding-bottom:0;">
					<div class="announcement-info">
						<span class="announcement-title">New study group matching feature is live</span>
						<span class="announcement-date">Mar 24, 2026</span>
					</div>
					<span class="container-tag">Update</span>
				</div>
			</section>
			<section class="content-card">
				<h2>Recent Threads</h2>
				<div class="announcement-container">
					<div class="announcement-info">
						<span class="announcement-title">Tips for Network Operating Systems exam?</span>
						<span class="announcement-date">by Anish K. - 2hr ago</span>
					</div>
					<div style="display:flex; gap:6px; align-items:center;">
						<img src="../../assets/icons/thread-comment.png" style="height: 14px; aspect-ratio:1; filter: brightness(50%);">
						<span style="font-weight: 500; color:white; filter:brightness(50%);">21</span>
					</div>
				</div>
				<div class="announcement-container">
					<div class="announcement-info">
						<span class="announcement-title">Looking for partners for Web Dev module</span>
						<span class="announcement-date">by Manjeel L. - 4hr ago</span>
					</div>
					<div style="display:flex; gap:6px; align-items:center;">
						<img src="../../assets/icons/thread-comment.png" style="height: 14px; aspect-ratio:1; filter: brightness(50%);">
						<span style="font-weight: 500; color:white; filter:brightness(50%);">41</span>
					</div>
				</div>
				<div class="announcement-container" style="padding-bottom:0;">
					<div class="announcement-info">
						<span class="announcement-title">Campus WiFi issues - anyone else?</span>
						<span class="announcement-date">by Tenzing S. - 7hr ago</span>
					</div>
					<div style="display:flex; gap:6px; align-items:center;">
						<img src="../../assets/icons/thread-comment.png" style="height: 14px; aspect-ratio:1; filter: brightness(50%);">
						<span style="font-weight: 500; color:white; filter:brightness(50%);">67</span>
					</div>
				</div>
			</section>
		</main>
	</body>
</html>