<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Contact Us - Islinkton</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/contact.css">
</head>
<body>

    <div class="alert-banner-success" id="successAlertBox" style="display: none;">
        Message sent successfully!
    </div>

    <div class="page-content">
        <main class="content-container">
            <div class="inner-block">
                <h1 class="page-main-title">Contact Us</h1>
                <p class="page-subtitle">Have questions or feedback? Drop us a line below.</p>

                <div class="contact-box">
                    <form action="#" method="POST" id="contactForm" onsubmit="handleMockSubmit(event)">
                        
                        <div class="error-container-box" id="validationErrorBox"></div>

                        <div class="form-group-block">
                            <label for="contactName">Your Name</label>
                            <input type="text" id="contactName" name="contactName" placeholder="e.g. Jane Doe">
                        </div>

                        <div class="form-group-block">
                            <label for="contactEmail">University Email</label>
                            <input type="email" id="contactEmail" name="contactEmail" placeholder="e.g. jane.doe@islingtoncollege.edu.np">
                        </div>

                        <div class="form-group-block">
                            <label for="contactMessage">Message</label>
                            <textarea id="contactMessage" name="contactMessage" rows="5" placeholder="Type your message here..."></textarea>
                        </div>

                        <button type="submit" class="submit-btn-action">Send Message</button>
                    </form>
                </div>

                <div class="back-home-box">
                    <a href="${pageContext.request.contextPath}/home" class="back-link">Back to Home</a>
                </div>
            </div>
        </main>
    </div>

    <script>
        function handleMockSubmit(event) {
            // Prevent the browser from refreshing or making an actual request
            event.preventDefault();

            const name = document.getElementById("contactName").value.trim();
            const email = document.getElementById("contactEmail").value.trim();
            const message = document.getElementById("contactMessage").value.trim();
            const errorBox = document.getElementById("validationErrorBox");
            const successBox = document.getElementById("successAlertBox");
            
            errorBox.style.display = "none";
            errorBox.innerHTML = "";

            // Simple data verification criteria validation checks
            if (!name || !email || !message) {
                errorBox.innerHTML = "All fields are strictly required.";
                errorBox.style.display = "block";
                return false;
            }

            if (name.length < 2) {
                errorBox.innerHTML = "Name must be at least 2 characters long.";
                errorBox.style.display = "block";
                return false;
            }

            const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
            if (!emailRegex.test(email)) {
                errorBox.innerHTML = "Please provide a valid email address structure.";
                errorBox.style.display = "block";
                return false;
            }

            if (message.length < 10) {
                errorBox.innerHTML = "Your message content must be at least 10 characters long.";
                errorBox.style.display = "block";
                return false;
            }

            // Fire the mock alert box immediately upon passing validation rules
            successBox.style.display = "block";
            document.getElementById("contactForm").reset();

            // Automatically animate the success window away after 3 seconds
            setTimeout(() => {
                successBox.style.transition = "opacity 0.4s ease, visibility 0.4s";
                successBox.style.opacity = "0";
                successBox.style.visibility = "hidden";
                
                // Reset styling parameters cleanly for the next run
                setTimeout(() => {
                    successBox.style.display = "none";
                    successBox.style.opacity = "1";
                    successBox.style.visibility = "visible";
                }, 400);
            }, 3000);

            return true;
        }
    </script>
</body>
</html>