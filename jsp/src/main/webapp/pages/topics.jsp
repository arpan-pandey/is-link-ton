<%@ page import="java.util.*" %>

<html>
	<head>
		<link rel="stylesheet" href="../css/styles.css">
	</head>
	<body>
		<jsp:include page="../components/header.jsp"></jsp:include>
		
		<main>
			<h2>TOPICS</h2>
			<div id="form--container">
				<form method="POST">
					<input type="text" name="enteredTopic" placeholder="Enter a topic">
					<button type="submit">Submit</button>
				</form>
				
				<ul>
					<% 
						List<String> topics = (List<String>) session.getAttribute("enteredTopic");
						String newTopic = request.getParameter("enteredTopic");
						
						if(newTopic != null && !newTopic.isEmpty()){
					    	topics.add(newTopic);
						}
						
						for(String t : topics) {
					%>
					
						<li> 
							<%= t %> - <a href="entries.jsp?topic=<%= t %>">View Entries</a>
						</li>
					
					<% } %>
				</ul>
			</div>
		</main>
	</body>
</html>