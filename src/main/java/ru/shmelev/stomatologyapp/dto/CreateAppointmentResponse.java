package ru.shmelev.stomatologyapp.dto;

public record CreateAppointmentResponse(
        String description,
        boolean requiresConfirmation
) {
}
