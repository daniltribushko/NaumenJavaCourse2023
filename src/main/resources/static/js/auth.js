const passwordInput = document.getElementById("password");
const passwordError = document.getElementById("passwordError");

passwordInput.addEventListener("input", function() {
    const passwordValue = passwordInput.value;

    if (passwordValue.length < 8 && passwordValue.length !== 0) {
        passwordError.style.display="block";
        passwordError.textContent = "Пароль должен быть не менее 8 символов";
        passwordInput.classList.add("error-input");
    } else {
        passwordError.style.display="none";
        passwordError.textContent = "";
        passwordInput.classList.remove("error-input");
    }
});