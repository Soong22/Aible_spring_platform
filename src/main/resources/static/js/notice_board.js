document.addEventListener("DOMContentLoaded", () => {
    const notices = [
        { id: 1, title: "전홍석 화이팅!", author: "관리자", date: "2025-01-16", views: 100000, important: true },
        { id: 2, title: "AI 24조 화이팅", author: "관리자", date: "2024-12-30", views: 7 },
        { id: 3, title: "Aivle 6기 화이팅", author: "관리자", date: "2024-09-02", views: 8 },
        // 데이터 추가
        { id: 4, title: "공지 4", author: "관리자", date: "2024-06-15", views: 20 },
        { id: 5, title: "공지 5", author: "관리자", date: "2024-05-10", views: 15 },
        { id: 6, title: "공지 6", author: "관리자", date: "2024-04-20", views: 12 },
        { id: 7, title: "공지 7", author: "관리자", date: "2024-03-11", views: 10 },
        { id: 8, title: "공지 8", author: "관리자", date: "2024-02-05", views: 5 },
    ];

    const table = document.getElementById("notice-table");
    const filterBtn = document.getElementById("filter-btn");
    const prevPageBtn = document.querySelector(".prev-page");
    const nextPageBtn = document.querySelector(".next-page");
    const pageNumberDisplay = document.querySelector(".page-number");

    const itemsPerPage = 3; // 한 페이지에 표시할 공지 수
    let currentPage = 1; // 현재 페이지

    function renderTable(data) {
        table.innerHTML = ""; // Clear table
        const sortedData = data.sort((a, b) => {
            if (b.important !== a.important) {
                return b.important - a.important; // 중요 공지가 우선
            }
            return new Date(b.date) - new Date(a.date); // 날짜 내림차순 정렬
        });

        const totalPages = Math.ceil(sortedData.length / itemsPerPage);
        const startIdx = (currentPage - 1) * itemsPerPage;
        const endIdx = startIdx + itemsPerPage;
        const pageData = sortedData.slice(startIdx, endIdx);

        pageData.forEach((notice, index) => {
            const row = document.createElement("tr");
            if (notice.important) row.classList.add("important");
            row.innerHTML = `
                <td>${startIdx + index + 1}</td>
                <td>${notice.title}</td>
                <td>${notice.author}</td>
                <td>${notice.date}</td>
                <td>${notice.views}</td>
            `;
            table.appendChild(row);
        });

        // Update pagination
        pageNumberDisplay.textContent = `${currentPage}/${totalPages}`;
        prevPageBtn.disabled = currentPage === 1; // 첫 페이지에서 비활성화
        nextPageBtn.disabled = currentPage === totalPages; // 마지막 페이지에서 비활성화
    }

    function filterNotices() {
        const startDate = new Date(document.getElementById("start-date").value);
        const endDate = new Date(document.getElementById("end-date").value);
        const filtered = notices.filter(notice => {
            const noticeDate = new Date(notice.date);
            return noticeDate >= startDate && noticeDate <= endDate;
        });
        currentPage = 1; // 필터 적용 시 페이지 초기화
        renderTable(filtered);
    }

    function changePage(offset) {
        currentPage += offset;
        renderTable(notices); // 필터링 적용 이전 데이터 렌더링
    }

    filterBtn.addEventListener("click", filterNotices);
    prevPageBtn.addEventListener("click", () => changePage(-1));
    nextPageBtn.addEventListener("click", () => changePage(1));

    renderTable(notices); // Initial render
});
