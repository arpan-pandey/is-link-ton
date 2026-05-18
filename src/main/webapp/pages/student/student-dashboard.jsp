<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="fullName" value="${user.name}" />

<%-- cleaning name --%>
<c:set var="cleanName" value="${fn:trim(fn:replace(fullName, '  ', ' '))}" />

<%-- getting firstname --%>
<c:set var="firstName" value="${fn:split(cleanName, ' ')[0]}" />

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
		<jsp:include page="../../components/student-header.jsp"></jsp:include>
		<main>
		</main>
	</body>
</html>