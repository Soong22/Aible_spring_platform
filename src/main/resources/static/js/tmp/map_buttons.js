// map_buttons.js

// DOM이 준비되면 실행
document.addEventListener('DOMContentLoaded', function() {
  // 모든 region-group 요소에 대해 마우스 엔터 이벤트 핸들러 등록
  document.querySelectorAll('.region-group').forEach(function(group) {
    group.addEventListener('mouseenter', function() {
      // 부모 SVG에 해당 그룹을 재배치하여 맨 마지막에 놓음(즉, 가장 위에 표시됨)
      this.parentNode.appendChild(this);
    });
  });
});

function handleClick(region) {
    // 지역에 따라 다른 페이지로 이동
    switch(region) {
        case '북구':
            window.location.href = '/region/buk.html'; // 북구 페이지로 이동
            break;
        case '동구':
            window.location.href = '/region/dong.html'; // 동구 페이지로 이동
            break;
        case '광산구':
            window.location.href = '/region/gwang.html'; // 광산구 페이지로 이동
            break;
        case '남구':
            window.location.href = '/region/nam.html'; // 남구 페이지로 이동
            break;
        case '서구':
            window.location.href = '/region/seo.html'; // 서구 페이지로 이동
            break;
        default:
            alert(`${region}를 선택하셨습니다!`);
    }
}
