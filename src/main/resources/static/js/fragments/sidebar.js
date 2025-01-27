document.addEventListener("DOMContentLoaded", function () {
    // --- 유틸리티 함수 ---
    /**
     * 활성화된 메뉴와 서브메뉴 상태 초기화
     */
    function clearActiveStates() {
        const menuItems = document.querySelectorAll(".team24-sidebar .team24-menu-item");
        menuItems.forEach(function (menuItem) {
            menuItem.classList.remove("active");
            const subMenu = menuItem.querySelector(".team24-sub-menu");
            if (subMenu) {
                subMenu.style.maxHeight = null;
            }
        });
        const subMenuLinks = document.querySelectorAll(".team24-sidebar .team24-sub-menu a");
        subMenuLinks.forEach(function (link) {
            link.classList.remove("active");
        });
        // 이전에 저장된 active 상태 제거
        localStorage.removeItem("activeMenu");
        localStorage.removeItem("activeSubMenu");
    }

    // 맨 처음 모든 활성 상태를 지움
    clearActiveStates();

    // --- 사이드바 관련 기능 ---
    const sidebars = document.querySelectorAll(".team24-sidebar");

    sidebars.forEach(function (sidebar) {
        // 각 사이드바 내의 toggle 버튼, 메뉴 아이템 등을 찾습니다.
        const toggleBtn = sidebar.querySelector(".team24-toggle-btn");
        const menuItems = sidebar.querySelectorAll(".team24-menu-item");

        // 홈 버튼: href가 "/" 인 경우, 부모 클릭 이벤트 전파 차단
        const homeLink = sidebar.querySelector(".team24-menu-item a.team24-menu-link[href='/']");
        if (homeLink) {
            homeLink.addEventListener("click", function (e) {
                // 활성화된 서브메뉴 및 메뉴 초기화
                // clearActiveStates();
                // 부모의 click 이벤트가 실행되지 않도록 차단
                e.stopPropagation();
                localStorage.setItem("sidebarState", "open"); // 사이드바 상태를 항상 "열림"으로 설정
                // 기본 링크 이동은 그대로 진행
                // localStorage.setItem("sidebarState", "closed"); // 홈 클릭 시 사이드바 닫기
                // sidebar.classList.remove("open");
                // toggleBtn.textContent = "☰";
            });
        }

        // 헤더 아이콘 클릭 시 사이드바 닫기
        const headerIcon = document.querySelector(".team24-header a[href='/']");
        if (headerIcon) {
            headerIcon.addEventListener("click", function () {
                clearActiveStates();
                // 사이드바가 열린 상태라면 닫기
                if (sidebar.classList.contains("open")) {
                    sidebar.classList.remove("open");
                    if (toggleBtn) {
                        toggleBtn.textContent = "☰";
                    }
                    localStorage.setItem("sidebarState", "open"); // 사이드바 상태 저장
                }
            });
        }

        /**
         * 주어진 메뉴 아이템에 속한 서브메뉴를 토글(열기/닫기)합니다.
         * - 서브메뉴가 없다면 아무 동작도 하지 않습니다.
         * - 이미 열려 있다면 닫고, 닫혀 있다면 엽니다.
         */
        function toggleSubMenu(menuItem) {
            const subMenu = menuItem.querySelector(".team24-sub-menu");
            if (!subMenu) return; // 서브메뉴가 없으면 패스

            if (menuItem.classList.contains("active")) {
                // 이미 열려 있다면 닫기
                menuItem.classList.remove("active");
                subMenu.style.maxHeight = null;
                localStorage.setItem("sidebarState", "closed");
            } else {
                // 닫혀 있다면 열기
                menuItem.classList.add("active");
                subMenu.style.maxHeight = subMenu.scrollHeight + "px";
                localStorage.setItem("sidebarState", "open");
            }
        }

        /**
         * 각 메뉴 아이템(.team24-menu-item)에 클릭 이벤트 부여
         * - 서브메뉴 내부(리스트 항목의 a 태그)를 클릭하면 기본 이동 허용
         * - 홈 버튼(href="/") 클릭 시 부모 토글 로직 미실행
         * - 사이드바가 닫혀 있으면 먼저 열고, 서브메뉴 토글
         */
        menuItems.forEach(function (menuItem) {
            menuItem.addEventListener("click", function (e) {
                // 1) 클릭 대상이 서브메뉴 내부라면(서브메뉴 항목 링크) 토글 로직 실행 안 함.
                if (e.target.closest(".team24-sub-menu")) {
                    return; // 서브메뉴 링크 클릭시 기본 이동 (페이지 이동 시 active 상태는 복원됨)
                }
                // // 2) 홈 버튼이면(메인 링크 "/") 토글 로직 미실행
                // const homeLinkInThisItem = menuItem.querySelector("a.team24-menu-link[href='/']");
                // if (homeLinkInThisItem && e.target.closest("a.team24-menu-link[href='/']")) {
                //     return;
                // }

                // 2) 홈 버튼이면(메인 링크 "/") 토글 로직 미실행
                const homeLinkInThisItem = menuItem.querySelector("a.team24-menu-link[href='/']");
                if (homeLinkInThisItem && e.target.closest("a.team24-menu-link[href='/']")) {
                    // 사이드바를 닫지 않고 active 상태만 초기화
                    // clearActiveStates(); // 필요한 경우에만 호출
                    e.stopPropagation(); // 부모 이벤트 전파 방지
                    return; // 기본 동작은 그대로 유지
                }


                // 기본 동작 막기 (예: "#" 링크 등 이동 방지)
                e.preventDefault();
                // 3) 사이드바가 닫혀 있으면 먼저 열기
                if (!sidebar.classList.contains("open")) {
                    sidebar.classList.add("open");
                    if (toggleBtn) {
                        toggleBtn.textContent = "<";
                    }
                }
                // 4) 해당 메뉴 아이템의 서브메뉴만 토글
                toggleSubMenu(menuItem);
            });
        });

        /**
         * 토글 버튼(☰ / <) 클릭 이벤트:
         * - 사이드바 자체를 열거나 닫음
         * - 닫을 경우 모든 서브메뉴도 닫음
         */
        if (toggleBtn) {
            toggleBtn.addEventListener("click", function () {
                const isOpen = sidebar.classList.toggle("open");
                if (isOpen) {
                    toggleBtn.textContent = "<";
                } else {
                    toggleBtn.textContent = "☰";
                    clearActiveStates(); // 모든 메뉴 초기화
                    localStorage.setItem("sidebarState", "closed");
                }
            });
        }

        // --- 서브메뉴 링크 클릭 시 active 클래스 부여 (개별 서브메뉴 항목) ---
        const subMenuLinks = sidebar.querySelectorAll(".team24-sub-menu a");
        subMenuLinks.forEach(function (link) {
            link.addEventListener("click", function () {
                // 모든 서브메뉴 링크에서 active 제거
                subMenuLinks.forEach(function (sibling) {
                    sibling.classList.remove("active");
                });
                // 클릭한 서브메뉴 링크에 active 추가
                this.classList.add("active");
                // active 상태 유지 로직 추가 (페이지 이동 후 복원)
                localStorage.setItem("activeSubMenu", this.getAttribute("href"));
            });
        });
    }); // end of sidebars.forEach

    // --- 사이드바 상태 복원 (localStorage 사용) ---
    const storedSidebarState = localStorage.getItem("sidebarState");
    const sidebar = document.querySelector(".team24-sidebar");
    if (storedSidebarState === "open" && sidebar) {
        sidebar.classList.add("open");
        const toggleBtn = sidebar.querySelector(".team24-toggle-btn");
        if (toggleBtn) {
            toggleBtn.textContent = "<";
        }
    } else if (storedSidebarState === "closed" && sidebar) {
        sidebar.classList.remove("open"); // 사이드바를 닫음
    }


    // --- 현재 페이지에 해당하는 서브 메뉴 활성화 ---
    // 먼저 모든 활성 상태를 초기화합니다.
    clearActiveStates();
    // 현재 페이지 경로 가져오기 (예: "/region/gwang.html")
    const currentPath = window.location.pathname;
    // 모든 서브 메뉴 링크를 순회하면서 현재 경로와 일치하는 링크에만 active 클래스 추가
    const allSubMenuLinks = document.querySelectorAll(".team24-sub-menu a");
    allSubMenuLinks.forEach(function (link) {
        if (link.getAttribute("href") === currentPath) {
            // 현재 경로와 일치하는 링크 활성화
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
});


