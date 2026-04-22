package ru.shmelev.stomatologyapp.dto.appointment;

public record CreateAppointmentResponse(
        String description,
        boolean requiresConfirmation
) {
}
