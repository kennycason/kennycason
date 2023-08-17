$(document).ready(() => {
    const modal = document.getElementById('modal');
    const modalClose = document.getElementById('modal-close');
    
    modalClose.addEventListener('click', function () {
        modal.style.display = "none";
    });
    modal.addEventListener('click', function () {
        modal.style.display = "none";
    });

    // global handler
    document.addEventListener('click', function (e) {
        if (e.target.className.indexOf('modal-target') !== -1) {
            const img = e.target;
            const modalImg = document.getElementById("modal-content");
            const captionText = document.getElementById("modal-caption");
            modal.style.display = "block";
            modalImg.src = img.src;
            captionText.innerHTML = img.alt;
        }
    });
});
