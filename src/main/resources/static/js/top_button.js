$(document).ready(function () {
    var topButton = $('#topButton');

    // 버튼이 숨겨져 있으면 강제로 표시
    topButton.show();

    // 버튼 클릭 시 최상단 이동
    topButton.click(function () {
        $('html, body').animate({ scrollTop: 0 }, 600);
        return false;
    });
});
