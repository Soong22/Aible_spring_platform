function isMobileDevice() {
    return /Mobi|Android|iPhone|iPad|iPod/i.test(navigator.userAgent);
}

function toggleLoginLogout() {
    let menu = document.getElementById("loginLogoutMenu");
    let windowWidth = window.innerWidth;
    let screenWidth = window.screen.availWidth; // 사용 가능한 화면 너비

    if (isMobileDevice() || windowWidth <= screenWidth / 2) {
        menu.style.display = "block";  // 모바일이거나 창 크기가 절반 이하일 때 보이기
    } else {
        menu.style.display = "none";   // 데스크탑에서 창 크기가 절반보다 크면 숨기기
    }
}

window.addEventListener("load", toggleLoginLogout);
window.addEventListener("resize", toggleLoginLogout);