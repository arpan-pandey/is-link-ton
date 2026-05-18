<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
     <c:if test="${not empty error}">
	    <div class="register-error-message" id="errorBox">
	        ${error}
	    </div>
	</c:if>
	
    <div class="register-container">
    
    	<h1 class="register-title">Islinkton</h1>
        	<p class="subtitle">Create your academic profile</p>
		
        <div class="register-box">
        
            <form action="${pageContext.request.contextPath}/register" method="post" enctype="multipart/form-data" id="registerForm">
                
                <div class="form-group">
                    <label for="fullName">Full Name</label>
                    <input type="text" id="fullName" name="fullName" placeholder="e.g; Jane Doe" required>
                </div>

                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" placeholder="e.g; janedoe67" required>
                </div>

                <div class="form-group">
                    <label for="email">University Email</label>
                    <input type="email" id="email" name="email" placeholder="e.g; jane.doe@islingtoncollege.edu.np" required>
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
                
				    <label>Profile Image</label>
				
				    <div class="upload-area" id="uploadArea">
				
				        <img id="imagePreview" src="#" alt="Preview">
				
				        <div class="upload-content" id="uploadContent">
				            <span class="upload-icon">+</span>
				            <p>Upload a file or drag and drop</p>
				            <small>PNG, JPG up to 5MB</small>
				        </div>
				
				        <input type="file"
				               id="profileImage"
				               name="profileImage"
				               accept="image/*"
				               onchange="previewFile()"
				               hidden>
				
				    </div>
				</div>

                <button type="submit" class="register-btn">Register Account</button>
            </form>

            <p class="signin-link">
                Already have an account? <a href="${pageContext.request.contextPath}/login">Sign in</a>
            </p>
        </div>
    </div>
</body>
<script>
    window.addEventListener("load", function () {
        const errorBox = document.getElementById("errorBox");

        if (errorBox) {
            setTimeout(() => {
                errorBox.classList.add("hide");
            }, 3000); // visible for 3 seconds
        }
    });

    function previewFile() {

        const preview =
            document.getElementById("imagePreview");

        const content =
            document.getElementById("uploadContent");

        const file =
            document.getElementById("profileImage").files[0];

        const reader = new FileReader();

        reader.onloadend = function() {

            preview.src = reader.result;

            preview.style.display = "block";

            content.style.display = "none";
        };

        if(file){
            reader.readAsDataURL(file);
        }
    }

    const uploadArea =
        document.getElementById("uploadArea");

    const fileInput =
        document.getElementById("profileImage");

    uploadArea.addEventListener("click", ()=> {
        fileInput.click();
    });

    uploadArea.addEventListener("dragover", (e)=>{

        e.preventDefault();

        uploadArea.style.borderColor="#0066cc";

    });

    uploadArea.addEventListener("dragleave", ()=>{

        uploadArea.style.borderColor="#ccc";

    });

    uploadArea.addEventListener("drop",(e)=>{

        e.preventDefault();

        uploadArea.style.borderColor="#ccc";

        if(e.dataTransfer.files.length){

            fileInput.files=e.dataTransfer.files;

            previewFile();

        }
    });
</script>
</html>