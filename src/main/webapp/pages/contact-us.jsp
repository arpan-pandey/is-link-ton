<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Islinkton - Contact-us</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<header class="navbar">
    <h2>Islinkton</h2>
    <nav>
        <a href="${pageContext.request.contextPath}/home">Home</a>
        <a href="#">Threads</a>
        <a href="#">Petitions</a>
        <a href="#">Study Groups</a>
        <a href="#">Resources</a>
        <a href="${pageContext.request.contextPath}/about">About</a>
        <a class="active" href="${pageContext.request.contextPath}/support">Support</a>
    </nav>
</header>

<main class="container">
    <section class="hero card">
        <h1>Contact Support</h1>
        <p>For academic inquiries, technical support, or to report a violation of the Honor Code.</p>
    </section>

    <section class="layout">
        <form class="card form-box">
            <h3>Send us a message</h3>

            <div class="form-row">
                <label>Name
                    <input type="text" placeholder="Your full name">
                </label>

                <label>Islinkton Email
                    <input type="email" placeholder="student@islinkton.edu">
                </label>
            </div>

            <label>Subject
                <select>
                    <option>Select a topic...</option>
                    <option>Technical Support</option>
                    <option>Academic Inquiry</option>
                    <option>Honor Code</option>
                </select>
            </label>

            <label>Message
                <textarea placeholder="Describe your issue or inquiry in detail..."></textarea>
            </label>

            <button type="submit">Submit Request</button>
        </form>

        <aside>
            <div class="card">
                <h3>Direct Contacts</h3>
                <p><strong>General Support</strong><br>support@islinkton.edu</p>
                <p><strong>Honor Council</strong><br>ethics@islinkton.edu</p>
                <p><strong>IT Help Desk</strong><br>Library, Room 402<br>Mon-Fri, 9am - 5pm</p>
            </div>

            <div class="card">
                <h3>Common Questions</h3>
                <p><strong>Account Access Issues?</strong><br>Ensure you are using your official email.</p>
                <p><strong>Thread Moderation</strong><br>Threads are actively monitored by faculty advisors.</p>
            </div>
        </aside>
    </section>
</main>

<footer>
    <strong>Islinkton</strong>
    <span>Honor Code</span>
    <span>Support</span>
    <span>Privacy Policy</span>
</footer>
</body>
</html>