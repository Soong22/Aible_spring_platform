document.addEventListener('DOMContentLoaded', () => {
    let isScrolling;

    // 스크롤 이벤트 감지
    window.addEventListener('scroll', () => {
        // `scrolling` 클래스를 추가하여 스크롤바 표시
        document.documentElement.classList.add('scrolling');

        // 스크롤이 멈춘 후 500ms 뒤에 `scrolling` 클래스 제거
        clearTimeout(isScrolling);
        isScrolling = setTimeout(() => {
            document.documentElement.classList.remove('scrolling');
        }, 500); // 0.5초 후 숨김
    });
});
