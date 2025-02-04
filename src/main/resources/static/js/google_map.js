function loadCCTVData() {
    const region = document.getElementById("region-select").value; // 선택된 구
    const dataUrl = `/cctv/cctv_data_${region}.json`; // 구별 JSON 파일 경로

    // 사용자 지정 CCTV 아이콘 경로 및 크기 설정
    const cctvIconUrl = {
        url: '/images/cctv2.png', // CCTV 아이콘 경로
        scaledSize: new google.maps.Size(40, 40), // 아이콘 크기 설정 (너비 x 높이)
    };

    // 기존 마커 제거
    if (window.markers) {
        window.markers.forEach(marker => marker.setMap(null));
    }
    window.markers = [];

    // JSON 데이터 로드 및 지도에 마커 추가
    fetch(dataUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            if (data.length === 0) {
                alert("해당 구에 대한 CCTV 데이터가 없습니다.");
                return;
            }

            data.forEach(cctv => {
                const marker = new google.maps.Marker({
                    position: { lat: cctv.lat, lng: cctv.lng },
                    map: map,
                    title: cctv.name,
                    icon: cctvIconUrl, // 사용자 지정 아이콘
                });

                const infoWindow = new google.maps.InfoWindow({
                    content: `<b>${cctv.name}</b>`,
                });

                marker.addListener("click", () => {
                    infoWindow.open(map, marker);
                });

                window.markers.push(marker); // 마커 저장
            });
        })
        .catch(error => {
            console.error("Error loading JSON:", error);
            alert("CCTV 데이터를 불러오는 중 오류가 발생했습니다.");
        });
}

// 지도 초기화 함수
function initMap() {
    const center = { lat: 35.159545, lng: 126.852601 }; // 광주광역시 중심 좌표
    map = new google.maps.Map(document.getElementById("map"), {
        zoom: 12,
        center: center,
    });
    loadCCTVData(); // 초기 데이터 로드
}

// 페이지 로드 시 지도 초기화
window.onload = initMap;
