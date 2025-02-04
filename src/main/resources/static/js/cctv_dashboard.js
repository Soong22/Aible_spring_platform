document.addEventListener("DOMContentLoaded", () => {
    const cctvUrls = [
        "http://your-cctv-stream-url-1",
        "http://your-cctv-stream-url-2",
        "http://your-cctv-stream-url-3",
        "http://your-cctv-stream-url-4",
        "http://your-cctv-stream-url-5",
        "http://your-cctv-stream-url-6",
        "http://your-cctv-stream-url-7",
        "http://your-cctv-stream-url-8"
    ];

    const itemsPerPage = 4; // 한 페이지에 표시할 CCTV 개수
    let currentPage = 1;

    const gridElement = document.getElementById("cctv-grid");
    const prevButton = document.getElementById("prevPage");
    const nextButton = document.getElementById("nextPage");
    const pageIndicator = document.getElementById("pageIndicator");

    const modal = document.getElementById("cctvModal");
    const modalVideo = document.getElementById("modalCctvVideo");
    const closeModalButton = document.querySelector(".close");

    // 모달 닫기
    function closeModal() {
        modal.style.display = "none"; // 모달 숨김
        modalVideo.pause(); // 비디오 재생 중지
        modalVideo.src = ""; // 스트림 제거
    }

    // 페이지 렌더링 함수
    function renderPage(page) {
        gridElement.innerHTML = ""; // 기존 CCTV 비디오 초기화

        const totalPages = Math.ceil(cctvUrls.length / itemsPerPage); // 전체 페이지 수 계산
        const startIndex = (page - 1) * itemsPerPage;
        const endIndex = Math.min(startIndex + itemsPerPage, cctvUrls.length);

        // 현재 페이지의 CCTV 추가
        for (let i = startIndex; i < endIndex; i++) {
            const videoElement = document.createElement("video");
            videoElement.src = cctvUrls[i];
            videoElement.autoplay = true;
            videoElement.controls = true;
            videoElement.muted = true;
            videoElement.style.cursor = "pointer"; // 클릭 가능한 커서

            // 클릭 이벤트 추가
            videoElement.addEventListener("click", () => {
                modal.style.display = "flex"; // 모달 표시
                modalVideo.src = cctvUrls[i]; // 클릭한 CCTV URL을 모달에 로드
                modalVideo.play(); // 재생
            });

            gridElement.appendChild(videoElement);

            // 비디오 스타일
            videoElement.style.width = "100%";
            videoElement.style.height = "100%";
            videoElement.style.objectFit = "cover";
            videoElement.style.border = "2px solid #34495e";
            videoElement.style.borderRadius = "10px";
            videoElement.style.background = "black";
        }

        // 페이지 인디케이터 업데이트
        pageIndicator.textContent = `${page} / ${totalPages}`; // 현재 페이지 / 전체 페이지 표시

        // 버튼 상태 업데이트
        prevButton.disabled = page === 1;
        nextButton.disabled = page === totalPages;
    }

    // 이전 페이지 버튼 클릭 이벤트
    prevButton.addEventListener("click", () => {
        if (currentPage > 1) {
            currentPage--;
            renderPage(currentPage);
        }
    });

    // 다음 페이지 버튼 클릭 이벤트
    nextButton.addEventListener("click", () => {
        if (currentPage < Math.ceil(cctvUrls.length / itemsPerPage)) {
            currentPage++;
            renderPage(currentPage);
        }
    });

    // 모달 닫기 버튼 이벤트
    closeModalButton.addEventListener("click", closeModal);

    // 모달 외부 클릭 시 닫기
    modal.addEventListener("click", (event) => {
        if (event.target === modal) {
            closeModal();
        }
    });

    renderPage(currentPage); // 초기 렌더링
});
