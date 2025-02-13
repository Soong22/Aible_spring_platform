let currentSlide = 0;
const slides = document.querySelectorAll('.team24-index-slider img');
const indicators = document.querySelectorAll('.team24-index-slider-indicator .team24-index-indicator');

function showSlide(index) {
    slides.forEach((slide, i) => {
        slide.style.display = (i === index) ? 'block' : 'none';
    });
    indicators.forEach((indicator, i) => {
        indicator.classList.toggle('active', i === index);
    });
}

function nextSlide() {
    currentSlide = (currentSlide + 1) % slides.length;
    showSlide(currentSlide);
}

function prevSlide() {
    currentSlide = (currentSlide - 1 + slides.length) % slides.length;
    showSlide(currentSlide);
}

function goToSlide(index) {
    currentSlide = index;
    showSlide(currentSlide);
}

window.addEventListener('load', () => {
    const sliderImage = slides[0];
    const imageWidth = sliderImage.offsetWidth;
    const boxWidth = imageWidth / 2;

    const boxes = document.querySelectorAll('.team24-index-info-box .team24-index-box');
    boxes.forEach(box => {
        box.style.width = boxWidth + 'px';
    });
});

// 초기 슬라이드를 표시
showSlide(currentSlide);

// 5초마다 자동으로 슬라이드 변경
setInterval(nextSlide, 5000);
