<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Admin Dashboard - Islinkton</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
		
		<style>
		
			body{
				background: #4A1E1E;
			}
		
			#sidebar{
				background-color: #4A1E1E;
				
				&>nav{
					border-top: 0.5px solid #6B2A2A;
					border-bottom: 0.5px solid #6B2A2A;
					
					&>a{
						&:hover{
							background: hsla(0, 45%, 30%, 0.5);
						}
					}
				}
			}
			
			#profile-icon{
				background: #5A2A2A;
			}
			
			#welcome-text{
				color: #7E3A37;
			}
			
		</style>
		
	</head>
	<body>
		<jsp:include page="../../components/admin/admin-sidebar.jsp"></jsp:include>
		<main>
			<header>
				<h1 id="page-title">Admin Dashboard</h1>
				<span id="welcome-text">Welcome back, Jane.</span>
			</header>
			<section class="content-card">
				<h2>Pending Thread Approvals</h2>
				<div class="announcement-container">
					<div class="announcement-info">
						<span class="announcement-title">Is the cafeteria closing early?</span>
						<span class="announcement-date">Mar 25, 2026</span>
					</div>
					<span class="container-tag" style="color:green;">✓</span>
					<span class="container-tag" style="color:red;">⨯</span>
				</div>
				<div class="announcement-container">
					<div class="announcement-info">
						<span class="announcement-title">Is the cafeteria closing early?</span>
						<span class="announcement-date">Mar 25, 2026</span>
					</div>
					<span class="container-tag" style="color:green;">✓</span>
					<span class="container-tag" style="color:red;">⨯</span>
				</div>
			</section>
			<section class="content-card">
				<h2>Manage Users</h2>
				<div class="announcement-container">
					<div class="announcement-info">
						<span class="announcement-title">Arjun Thapaliya</span>
						<span class="announcement-date">arjunthapaliya@islingtoncollege.edu.np</span>
					</div>
					<span class="container-tag">Admin</span>
					<span class="container-tag" style="color:red;">⨯</span>
				</div>
				<div class="announcement-container">
					<div class="announcement-info">
						<span class="announcement-title">Jenish Shah</span>
						<span class="announcement-date">np0144a@islingtoncollege.edu.np</span>
					</div>
					<span class="container-tag">Student</span>
					<span class="container-tag" style="color:red;">⨯</span>
				</div>
				<div class="announcement-container">
					<div class="announcement-info">
						<span class="announcement-title">Himal Pandit</span>
						<span class="announcement-date">np0144b@islingtoncollege.edu.np</span>
					</div>
					<span class="container-tag">Student</span>
					<span class="container-tag" style="color:red;">⨯</span>
				</div>
			</section>
		</main>
	</body>
</html>