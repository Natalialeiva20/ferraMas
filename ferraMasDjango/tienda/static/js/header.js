let lastScrollTop = 0;
const header = document.querySelector('.header-hide-show');

window.addEventListener('scroll', function () {
    let scrollTop = window.pageYOffset || document.documentElement.scrollTop;

    if (scrollTop === 0) {
        header.style.transform = 'translateY(0)';
        header.style.transition = 'transform 0.3s ease';
    } else if (scrollTop > lastScrollTop) {
        header.style.transform = 'translateY(-100%)';
        header.style.transition = 'transform 0.3s ease';
    } else {
        header.style.transform = 'translateY(0)';
        header.style.transition = 'transform 0.3s ease';
    }

    lastScrollTop = scrollTop;
});