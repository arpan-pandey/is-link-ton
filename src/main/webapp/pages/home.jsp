<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Islinkton - Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<header class="navbar">
    <h2>Islinkton</h2>
    <nav>
        <a class="active" href="${pageContext.request.contextPath}/home">Home</a>
        <a href="${pageContext.request.contextPath}/threads">Threads</a>
        <a href="#">Petitions</a>
        <a href="#">Study Groups</a>
        <a href="${pageContext.request.contextPath}/resources">Resources</a>
        <a href="${pageContext.request.contextPath}/about">About</a>
        <a href="${pageContext.request.contextPath}/contact-us">Contact Us</a>
    </nav>
</header>

<main class="container">
    <h1>Welcome to Islinkton</h1>
    <p class="lead">The central hub for academic discourse, student petitions, and collaborative resources.</p>

    <section class="content-layout">
        <div>
            <div class="section-header">
                <h3>Recent Threads</h3>
                <a href="#">View All →</a>
            </div>

            <div class="thread-card">
                <div class="votes">142<br>⌃</div>
                <div>
                    <span class="tag">Computer Science</span>
                    <small> Posted by u/algo_student 2h ago</small>
                    <h3>Thoughts on the proposed changes to the CS curriculum for Fall 2025?</h3>
                    <p>I've been reviewing the newly published draft for the CS core requirements, and while adding more ML courses is great, reducing systems requirements seems like a mistake.</p>
                    <small>45 Comments · Save</small>
                </div>
            </div>

            <div class="thread-card">
                <div class="votes">89<br>⌃</div>
                <div>
                    <span class="tag">Campus Life</span>
                    <small> Posted by u/library_dweller 5h ago</small>
                    <h3>The main library needs more accessible outlets on the 3rd floor.</h3>
                    <p>It's nearly impossible to find a spot with a working power outlet during midterms week.</p>
                    <small>22 Comments · Save</small>
                </div>
            </div>
        </div>

        <aside class="card">
            <h3>Trending Petitions</h3>
            <hr>
            <strong>Extend Library Hours During Finals</strong>
            <div class="progress"><span></span></div>
            <small>8,500 / 10,000 Signatures</small>
        </aside>
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