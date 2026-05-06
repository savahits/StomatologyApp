const modal = document.getElementById("deleteModal");

function openModal() {
    modal.classList.add("active");
}

function closeModal() {
    modal.classList.remove("active");
}

modal.addEventListener("click", (e) => {
    if (e.target === modal) {
        closeModal();
    }
});

