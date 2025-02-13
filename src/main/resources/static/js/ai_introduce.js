document.addEventListener("DOMContentLoaded", function () {
    const navLinks = document.querySelectorAll(".nav-link");

    // 클릭 시 부드럽게 이동
    navLinks.forEach(link => {
        link.addEventListener("click", function (e) {
            e.preventDefault();
            const targetId = this.getAttribute("href").substring(1);
            document.getElementById(targetId).scrollIntoView({ behavior: "smooth" });
        });
    });

    // 현재 위치한 섹션을 감지하여 네비게이션 강조
    window.addEventListener("scroll", function () {
        let scrollPos = window.scrollY + window.innerHeight / 2;
        navLinks.forEach(link => link.classList.remove("active"));

        navLinks.forEach(link => {
            let section = document.querySelector(link.getAttribute("href"));
            if (section.offsetTop <= scrollPos && section.offsetTop + section.offsetHeight > scrollPos) {
                link.classList.add("active");
            }
        });
    });
});
