<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
    
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Resources - Islinkton</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/create-form.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pages/resources.css">
</head>
<body>
    <jsp:include page="/components/user-header.jsp" />
    
    <%-- Success Message Notification --%>
		<c:if test="${not empty sessionScope.message}">
		    <div id="successBox" class="popup-message success-toast">
		        <c:out value="${sessionScope.message}"/>
		    </div>
		    <c:remove var="message" scope="session" />
		</c:if>
		
		<%-- Error Message Notification --%>
		<c:if test="${not empty sessionScope.error}">
		    <div id="errorBox" class="popup-message">
		        <c:out value="${sessionScope.error}"/>
		    </div>
		    <c:remove var="error" scope="session" />
		</c:if>
    
    <main class="form-main-container">
        <div class="form-card">
            <div class="form-header">
                <h2>Upload New Resource</h2>
                <p>Upload helpful lecture sheets, reference materials, or assignment guidelines for your classes.</p>
            </div>

            <form action="${pageContext.request.contextPath}/resources/upload" method="POST" enctype="multipart/form-data" class="academic-form">
                
                <div class="form-group">
                    <label for="resourceTitle">Resource Title</label>
                    <input 
                        type="text" 
                        id="resourceTitle" 
                        name="title" 
                        required 
                        placeholder="e.g., 'Advanced Database Assignment Sheet'" 
                    />
                </div>

                <div class="form-group">
                    <label for="resourceDescription">Resource Description</label>
                    <textarea 
                        id="resourceDescription" 
                        name="description" 
                        rows="4" 
                        placeholder="Describe the file attachment details..."
                    ></textarea>
                </div>

                <div class="form-group">
                    <label for="resourceCategory">Academic Category</label>
                    <select id="resourceCategory" name="categoryId" required>
                        <option value="" disabled selected>Select a Suitable Flair</option>
                        <c:forEach var="category" items="${categories}">
                            <option value="${category.id}">
                                <c:out value="${category.name}"/>
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <div class="form-group">
				    <label>Target File Attachment</label>
				    
				    <div id="uploadArea" class="upload-area">
				        <div id="uploadContent" class="upload-content">
				            <span class="upload-icon">+</span>
				            <p id="uploadInstructions">Drag & drop your resource file here, or click to browse</p>
				        </div>
				    </div>
				    
				    <input 
				        type="file" 
				        id="resourceFile" 
				        name="file" 
				        required 
				        style="display: none;" 
				        onchange="handleFileSelection()"
				    />
				</div>

                <div class="form-actions">
                    <button type="submit" class="btn btn-primary">Publish File Asset</button>
                    <a href="${pageContext.request.contextPath}/resources" class="btn btn-secondary">Cancel</a>
                </div>
            </form>
        </div>
    </main>
    
    <jsp:include page="/components/footer.jsp" />
</body>

<script>

    // shows file name
    function handleFileSelection() {
        const fileInput = document.getElementById("resourceFile");
        const instructions = document.getElementById("uploadInstructions");
        
        if (fileInput.files.length > 0) {
            const file = fileInput.files[0];
            instructions.innerHTML = "<strong>Selected File:</strong> " + file.name + " (" + formatBytes(file.size) + ")";
            document.getElementById("uploadArea").style.borderColor = "#0066cc";
        }
    }

    // helper utility to make file sizes readable
    function formatBytes(bytes) {
        if (bytes === 0) return '0 Bytes';
        const k = 1024;
        const sizes = ['Bytes', 'KB', 'MB'];
        const i = Math.floor(Math.log(bytes) / Math.log(k));
        return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i];
    }

    const uploadArea = document.getElementById("uploadArea");
    const fileInput = document.getElementById("resourceFile");

    // click area trigger pipeline redirection
    uploadArea.addEventListener("click", () => {
        fileInput.click();
    });

    uploadArea.addEventListener("dragover", (e) => {
        e.preventDefault();
        uploadArea.style.borderColor = "#0066cc";
    });

    uploadArea.addEventListener("dragleave", () => {
        uploadArea.style.borderColor = "#ccc";
    });

    uploadArea.addEventListener("drop", (e) => {
        e.preventDefault();
        uploadArea.style.borderColor = "#ccc";

        if (e.dataTransfer.files.length) {
            fileInput.files = e.dataTransfer.files;
            handleFileSelection();
        }
    });
</script>
</html>