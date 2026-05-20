<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Islinkton - About</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="navbar">
    <h2>Islinkton</h2>
    <nav>
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/threads">Threads</a>
        <a href="#">Petitions</a>
        <a href="#">Study Groups</a>
        <a href="${pageContext.request.contextPath}/resources">Resources</a>
        <a class="active" href="${pageContext.request.contextPath}/about">About</a>
        <a href="${pageContext.request.contextPath}/contact-us">Contact Us</a>
    </nav>
</header>

<main class="container">
    <h1>About Islinkton</h1>
    <p class="lead">
        Islinkton is an institutional utility platform designed to facilitate focused academic discourse,
        structured resource sharing, and community coordination.
    </p>

    <section class="two-column">
        <div class="card">
            <h2>Our Mission</h2>
            <p>To cultivate a structured digital campus that respects users' time and attention.</p>
        </div>

        <div class="card">
            <h2>Platform Purpose</h2>
            <ul>
                <li>Provide a permanent, searchable repository of academic discussions.</li>
                <li>Enable structured organization of study groups and resources.</li>
                <li>Facilitate transparent community governance through petitions.</li>
            </ul>
        </div>
    </section>

    <h2>Core Team</h2>

    <section class="team-grid">
        <div class="card profile">
            <img src="https://via.placeholder.com/90" alt="Robert Chen">
            <h3>Dr. Robert Chen</h3>
            <p>Faculty Advisor</p>
        </div>

        <div class="card profile">
            <img src="https://via.placeholder.com/90" alt="Sarah Jenkins">
            <h3>Sarah Jenkins</h3>
            <p>Lead Moderator</p>
        </div>

        <div class="card profile">
            <img src="https://via.placeholder.com/90" alt="David Alaba">
            <h3>David Alaba</h3>
            <p>Systems Architect</p>
        </div>
    </section>
</main>

<footer>
    <strong>Islinkton</strong>
    <span>Honor Code</span>
    <a href="${pageContext.request.contextPath}/contact-us">Contact Us</a>
    <span>Privacy Policy</span>
</footer>

</body>
</html>