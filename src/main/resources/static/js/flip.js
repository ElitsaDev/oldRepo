const sign_up = document.getElementById("sign-up");
const sign_in = document.getElementById("sign-in");
const container = document.getElementById("container");

sign_up.addEventListener("click",()=>{
   container.classList.add("login-mode");
});

sign_in.addEventListener("click",()=>{
    container.classList.remove("login-mode");
});

