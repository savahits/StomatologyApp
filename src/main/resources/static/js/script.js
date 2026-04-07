// Modal functions for appointment done confirmation
function openDoneModal(button) {
    const modal = document.getElementById('doneModal');
    const appointmentInfo = document.getElementById('appointmentInfo');
    const modalAppointmentId = document.getElementById('modalAppointmentId');

    // Get data from button attributes
    const appointmentId = button.getAttribute('data-appointment-id');
    const clientName = button.getAttribute('data-client-name');
    const doctorName = button.getAttribute('data-doctor-name');
    const appointmentTime = button.getAttribute('data-appointment-time');

    // Set the appointment ID in the hidden input
    modalAppointmentId.value = appointmentId;

    // Display appointment information
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

    // Show modal
    modal.style.display = 'block';
}

function closeDoneModal() {
    const modal = document.getElementById('doneModal');
    modal.style.display = 'none';
}

// Close modal when clicking outside of it
window.onclick = function(event) {
    const modal = document.getElementById('doneModal');
    if (event.target === modal) {
        modal.style.display = 'none';
    }
}


