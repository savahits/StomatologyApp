package ru.shmelev.stomatologyapp.exception;

import java.time.LocalDateTime;

public class AppointmentAlreadyExistsException extends RuntimeException {

    public AppointmentAlreadyExistsException(LocalDateTime appointmentTime, Long doctorId) {
        super(String.format("Запись на %1$tF %1$tT уже существует для этого врача",
                appointmentTime));
    }
}
