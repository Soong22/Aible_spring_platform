function toggleLoginLogout() {
    let menu = document.getElementById("loginLogoutMenu");
    let windowWidth = window.innerWidth;
    let screenWidth = window.screen.availWidth; // 가용 가능한 화면 너비 사용

    if (windowWidth <= screenWidth / 2) {
        menu.style.display = "block";  // 창 크기가 절반 이하일 때 보이기
    } else {
        menu.style.display = "none";   // 창 크기가 절반보다 클 때 숨기기
    }
}

window.addEventListener("load", toggleLoginLogout);
window.addEventListener("resize", toggleLoginLogout);
