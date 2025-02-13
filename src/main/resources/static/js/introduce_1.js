let currentIndex = 0;
const sections = document.querySelectorAll(".fullpage-section");

window.addEventListener("wheel", (event) => {
    if (event.deltaY > 0) {
        currentIndex = Math.min(currentIndex + 1, sections.length - 1);
    } else {
        currentIndex = Math.max(currentIndex - 1, 0);
    }
    window.scrollTo({
        top: window.innerHeight * currentIndex,
        behavior: "smooth"
    });
});
