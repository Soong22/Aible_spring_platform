// map_buttons.js
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
