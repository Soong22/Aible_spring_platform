document.addEventListener("DOMContentLoaded", function () {
    // --- 사이드바 관련 기능 ---
    const sidebar = document.querySelector(".team24-sidebar");
    const toggleBtn = sidebar?.querySelector(".team24-toggle-btn");
    const menuItems = sidebar?.querySelectorAll(".team24-menu-item");
    const subMenuLinks = sidebar?.querySelectorAll(".team24-sub-menu a");
    const homeButton = document.querySelector(".team24-header a[href='/']"); // KT 로고 (홈 버튼)

    /**
     * 모든 서브메뉴를 닫고 active 상태 초기화
     */
    function closeAllSubMenus() {
        menuItems.forEach(menuItem => {
            menuItem.classList.remove("active");
            const subMenu = menuItem.querySelector(".team24-sub-menu");
            if (subMenu) {
                subMenu.style.maxHeight = null;
            }
        });
        localStorage.removeItem("activeSubMenu");
    }

    // --- 홈 버튼 클릭 시 active 상태 초기화 ---
    if (homeButton) {
        homeButton.addEventListener("click", function () {
            localStorage.removeItem("activeSubMenu"); // 🌟 홈 버튼 클릭 시 서브메뉴 상태 초기화
            closeAllSubMenus(); // 🌟 사이드바 상태도 리셋
        });
    }

    // --- 사이드바 상태 복원 (localStorage 사용) ---
    const storedSidebarState = localStorage.getItem("sidebarState");

    if (storedSidebarState === "open" && sidebar) {
        sidebar.classList.add("open");
        if (toggleBtn) toggleBtn.textContent = "<";
    } else {
        sidebar.classList.remove("open");
        closeAllSubMenus();  // 🌟 사이드바가 닫혀 있다면 서브메뉴도 자동으로 닫기
    }

    // --- 현재 페이지의 서브메뉴 활성화 유지 ---
    const currentPath = window.location.pathname;
    const storedActiveSubMenu = localStorage.getItem("activeSubMenu");

    if (storedSidebarState === "open") {
        subMenuLinks.forEach(function (link) {
            if (link.getAttribute("href") === currentPath || link.getAttribute("href") === storedActiveSubMenu) {
                link.classList.add("active");

                const menuItem = link.closest(".team24-menu-item");
                if (menuItem) {
                    menuItem.classList.add("active");
                    const subMenu = menuItem.querySelector(".team24-sub-menu");
                    if (subMenu) {
                        subMenu.style.maxHeight = subMenu.scrollHeight + "px";
                    }
                }
            }
        });
    }

    // --- 메뉴 클릭 시 동작 ---
    menuItems?.forEach(function (menuItem) {
        menuItem.addEventListener("click", function (e) {
            // 서브메뉴 내부 링크 클릭 시 저장
            if (e.target.closest(".team24-sub-menu a")) {
                localStorage.setItem("activeSubMenu", e.target.getAttribute("href"));
                return;
            }

            // 사이드바가 닫혀 있으면 먼저 열기
            if (!sidebar.classList.contains("open")) {
                sidebar.classList.add("open");
                if (toggleBtn) toggleBtn.textContent = "<";
            }

            // 해당 메뉴 아이템의 서브메뉴 토글
            const subMenu = menuItem.querySelector(".team24-sub-menu");
            if (subMenu) {
                if (menuItem.classList.contains("active")) {
                    menuItem.classList.remove("active");
                    subMenu.style.maxHeight = null;
                } else {
                    menuItem.classList.add("active");
                    subMenu.style.maxHeight = subMenu.scrollHeight + "px";
                }
            }
        });
    });

    // --- 토글 버튼 동작 ---
    toggleBtn?.addEventListener("click", function () {
        const isOpen = sidebar.classList.toggle("open");
        toggleBtn.textContent = isOpen ? "<" : "☰";

        if (!isOpen) {
            localStorage.setItem("sidebarState", "closed");
            closeAllSubMenus(); // 🌟 사이드바 닫을 때 서브메뉴도 닫기
        } else {
            localStorage.setItem("sidebarState", "open");
        }
    });
});
