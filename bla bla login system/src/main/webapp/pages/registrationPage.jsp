<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Register Student</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/register.css">
</head>
<body>
    <h2>Student Registration</h2>
    <form action="${pageContext.request.contextPath}/register"
     method="post">
        First Name: <input type="text" name="first_name" required><br>
        Last Name: <input type="text" name="last_name" required><br>
        Username: <input type="text" name="username" required><br>
        DOB: <input type="date" name="dob" required><br>
        Gender: 
        <select name="gender" required>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
        </select><br>
        Email: 
        <input type="email" name="email" required><br>
        Number: 
        <input type="text" name="number" required><br>
        Password: 
        <input type="password" name="password" required><br>
        Program: 
        <select name="program" required>
            <option value="2">Bachelor in computing</option>
            <option value="5">Bachelor in Multimedia</option>
            <option value="6">Bachelor in networking</option>
        </select><br><br>
        <input type="submit" value="Register">
    </form>
</body>
</html>