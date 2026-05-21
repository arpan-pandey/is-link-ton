<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<footer class="footer center">
	<span>&copy; 2026 Islinkton. For academic discourse only.</span>
</footer>

<script>
    window.addEventListener("load", function () {
        const errorBox = document.getElementById("errorBox");
        const successBox = document.getElementById("successBox");

        if (errorBox) {
            setTimeout(() => {
                errorBox.classList.add("hide");
            }, 3000);
        }

        if (successBox) {
            setTimeout(() => {
                successBox.classList.add("hide");
            }, 3000);
        }
    });
</script>