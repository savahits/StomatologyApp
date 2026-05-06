function openDoneModal(button) {
    const modal = document.getElementById('doneModal');
    const appointmentInfo = document.getElementById('appointmentInfo');
    const modalAppointmentId = document.getElementById('modalAppointmentId');

    const appointmentId = button.getAttribute('data-appointment-id');
    const clientName = button.getAttribute('data-client-name');
    const doctorName = button.getAttribute('data-doctor-name');
    const appointmentTime = button.getAttribute('data-appointment-time');

    modalAppointmentId.value = appointmentId;

    appointmentInfo.innerHTML = `
        <div class="appointment-info-item">
            <span class="info-label">Клиент:</span>
            <span class="info-value">${clientName}</span>
        </div>
        <div class="appointment-info-item">
            <span class="info-label">Врач:</span>
            <span class="info-value">${doctorName}</span>
        </div>
        <div class="appointment-info-item">
            <span class="info-label">Дата и время:</span>
            <span class="info-value">${appointmentTime}</span>
        </div>
    `;

    modal.classList.add('active');
}

function closeDoneModal() {
    const modal = document.getElementById('doneModal');
    modal.classList.remove('active');
}

function openModal() {
    const modal = document.getElementById('confirmModal');
    modal.classList.add('active');
}

function closeModal() {
    const modal = document.getElementById('confirmModal');
    modal.classList.remove('active');
}

function submitForm() {
    document.getElementById('adminForm').submit();
}

window.onclick = function(event) {
    const confirmModal = document.getElementById('confirmModal');
    const doneModal = document.getElementById('doneModal');
    if (event.target === confirmModal) {
        confirmModal.classList.remove('active');
    }
    if (event.target === doneModal) {
        doneModal.classList.remove('active');
    }
}
