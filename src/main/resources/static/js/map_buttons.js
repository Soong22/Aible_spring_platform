// map_buttons.js
function handleClick(region) {
    // 지역에 따라 다른 페이지로 이동
    switch(region) {
        case '북구':
            window.location.href = '/members/광주북부'; // 북구 페이지로 이동
            break;
        case '동구':
            window.location.href = '/members/광주동부'; // 동구 페이지로 이동
            break;
        case '광산구':
            window.location.href = '/members/광주광산'; // 광산구 페이지로 이동
            break;
        case '남구':
            window.location.href = '/members/광주남부'; // 남구 페이지로 이동
            break;
        case '서구':
            window.location.href = '/members/광주서부'; // 서구 페이지로 이동
            break;
        default:
            alert(`${region}를 선택하셨습니다!`);
    }
}
