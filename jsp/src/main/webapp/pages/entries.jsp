<html>
	<head>
		<link rel="stylesheet" href="../css/styles.css">
	</head>
	<body>
		<jsp:include page="../components/header.jsp"></jsp:include>
		
		<main>
			<h2><% String topic = request.getParameter("topic"); %><%=topic %></h2>
		</main>
	</body>
</html>
