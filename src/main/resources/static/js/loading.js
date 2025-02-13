document.addEventListener('DOMContentLoaded', () => {
    // 🔥 로딩 화면 페이드아웃 처리
    setTimeout(() => {
        document.getElementById("loading-screen").style.opacity = "0";
        setTimeout(() => {
            document.getElementById("loading-screen").style.display = "none";
            document.querySelector('.container').style.opacity = "1"; // 🚀 배경 포함 페이지 표시
        }, 300);
    }, 600);
});
