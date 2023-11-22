const passwordInput = document.getElementById("password");
const passwordConfirm = document.getElementById("passwordConfirm");
const passwordError = document.getElementById("passwordError");
const passwordErrorConfirm = document.getElementById("passwordErrorConfirm");

passwordInput.addEventListener("input", function() {
    const passwordValue = passwordInput.value;
    const passwordConfirmValue = passwordConfirm.value;

    if (passwordValue.length < 8 && passwordValue.length !== 0) {
        passwordError.style.display="block";
        passwordError.textContent = "Пароль должен быть не менее 8 символов";
        passwordInput.classList.add("error-input");
    } else {
        passwordError.style.display="none";
        passwordError.textContent = "";
        passwordInput.classList.remove("error-input");
    }

    if((passwordValue!==passwordConfirmValue)&&(passwordValue.length>=8)&&(passwordConfirmValue.length!==0)){
        passwordErrorConfirm.style.display="block";
        passwordErrorConfirm.textContent = "Пароли не совпадают";
        passwordConfirm.classList.add("error-input")
    } else {
        passwordErrorConfirm.style.display="none";
        passwordErrorConfirm.textContent = "";
        passwordConfirm.classList.remove("error-input");
}

});
passwordConfirm.addEventListener("input", function (){
    const passwordConfirmValue = passwordConfirm.value;
    const passwordValue = passwordInput.value;

    if((passwordValue!==passwordConfirmValue)&&(passwordValue.length>=8)&&(passwordConfirmValue.length!==0)){
        passwordErrorConfirm.style.display="block";
        passwordErrorConfirm.textContent = "Пароли не совпадают";
        passwordConfirm.classList.add("error-input")
    } else {
        passwordErrorConfirm.style.display="none";
        passwordErrorConfirm.textContent = "";
        passwordConfirm.classList.remove("error-input");
    }
});
