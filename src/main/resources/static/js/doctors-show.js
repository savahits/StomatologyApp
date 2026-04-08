// Modal for doctor deletion
const modal = document.getElementById("deleteModal");

function openModal() {
    modal.classList.add("active");
}

function closeModal() {
    modal.classList.remove("active");
}

// Close modal when clicking outside of it
modal.addEventListener("click", (e) => {
    if (e.target === modal) {
        closeModal();
    }
});

