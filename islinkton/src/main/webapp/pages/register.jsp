<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Islinkton</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/register.css">
</head>
<body>
    <div class="register-container">
   		<h1 class="register-title">Islinkton</h1>
        <p class="subtitle">Create your academic profile</p>
        <div class="register-box">
            <form action="RegisterServlet" method="post" enctype="multipart/form-data" id="registerForm">
                
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" placeholder="Jane Doe" required>
                </div>

                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" placeholder="janedoe99" required>
                </div>

                <div class="form-group">
                    <label for="email">University Email</label>
                    <input type="email" id="email" name="email" placeholder="jane.doe@islinkton.edu" required>
                </div>

                <div class="form-group">
                    <label>Academic Role</label>
                    <div class="radio-group">
                        <label>
                            <input type="radio" name="role" value="Student" checked> Student
                        </label>
                        <label>
                            <input type="radio" name="role" value="Faculty"> Faculty
                        </label>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-group">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" required>
                    </div>
                    <div class="form-group">
                        <label for="confirmPassword">Confirm Password</label>
                        <input type="password" id="confirmPassword" name="confirmPassword" required>
                    </div>
                </div>

                <div class="form-group">
                    <label>Profile Image (Optional)</label>
                    <div class="upload-area" id="uploadArea">
                        <input type="file" id="profileImage" name="profileImage" accept="image/png, image/jpeg" style="display:none;">
                        <div class="upload-content">
                            <span class="upload-icon">📁</span>
                            <p>Upload a file or drag and drop</p>
                            <small>PNG, JPG up to 5MB</small>
                        </div>
                    </div>
                </div>

                <button type="submit" class="register-btn">Register Account</button>
            </form>

            <p class="signin-link">
                Already have an account? <a href="login.jsp">Sign in</a>
            </p>
        </div>
    </div>

    <script>
        // simple client-side validation
        document.getElementById('registerForm').addEventListener('submit', function(e) {
            const pass = document.getElementById('password').value;
            const confirm = document.getElementById('confirmPassword').value;
            
            if (pass !== confirm) {
                e.preventDefault();
                alert("Passwords do not match!");
            }
        });

        // drag & drop + click upload
        const uploadArea = document.getElementById('uploadArea');
        const fileInput = document.getElementById('profileImage');

        uploadArea.addEventListener('click', () => fileInput.click());

        uploadArea.addEventListener('dragover', (e) => {
            e.preventDefault();
            uploadArea.style.borderColor = '#0066cc';
        });

        uploadArea.addEventListener('dragleave', () => {
            uploadArea.style.borderColor = '#ccc';
        });

        uploadArea.addEventListener('drop', (e) => {
            e.preventDefault();
            uploadArea.style.borderColor = '#ccc';
            if (e.dataTransfer.files.length) {
                fileInput.files = e.dataTransfer.files;
            }
        });
    </script>
</body>
</html>