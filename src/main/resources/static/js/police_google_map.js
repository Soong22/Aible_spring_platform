function loadCCTVData() {
    const region = document.getElementById("region-select").value; // 선택된 구
    const dataUrl = `/police.json`; // JSON 파일 경로

    // 사용자 지정 경찰서 아이콘 설정
    const policeIcon = {
        url: '/images/police_icon.png',
        scaledSize: new google.maps.Size(40, 40), // 아이콘 크기 설정
    };

    // 기존 마커 제거
    if (window.markers) {
        window.markers.forEach(marker => marker.setMap(null));
    }
    window.markers = [];

    fetch(dataUrl)
        .then(response => response.json())
        .then(data => {
            // 선택된 구(region)에 해당하는 경찰서만 필터링
            const filteredData = data.filter(police => police.주소.includes(region));

            if (filteredData.length === 0) {
                alert("해당 구에 대한 경찰서 데이터가 없습니다.");
                return;
            }

            filteredData.forEach(police => {
                const fullName = `${police.시도청} ${police.경찰서} ${police.관서명} ${police.구분}`; // 시도청 + 경찰서 + 관서명 + 구분 합치기

                const marker = new google.maps.Marker({
                    position: { lat: police.위도, lng: police.경도 },
                    map: map,
                    title: fullName, // 마커에 툴팁 표시
                    icon: policeIcon, // 사용자 지정 아이콘
                });

                // 정보 창 (InfoWindow) 생성
                const infoWindow = new google.maps.InfoWindow({
                    content: `<b>${fullName}</b><br>${police.주소}`, // 마커 클릭 시 전체 명칭 + 주소 표시
                });

                // 마커 클릭 시 정보 창 열기
                marker.addListener("click", () => {
                    infoWindow.open(map, marker);
                });

                window.markers.push(marker);
            });
        })
        .catch(error => {
            console.error("Error loading JSON:", error);
            alert("경찰서 데이터를 불러오는 중 오류가 발생했습니다.");
        });
}

// 지도 초기화 함수
function initMap() {
    const center = { lat: 35.159545, lng: 126.852601 }; // 광주광역시 중심 좌표
    window.map = new google.maps.Map(document.getElementById("map"), {
        zoom: 12,
        center: center,
    });
    loadCCTVData(); // 초기 데이터 로드
}

// 페이지 로드 시 지도 초기화
window.onload = initMap;
