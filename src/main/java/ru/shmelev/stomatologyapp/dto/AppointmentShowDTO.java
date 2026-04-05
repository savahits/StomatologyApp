package ru.shmelev.stomatologyapp.dto;

import java.time.LocalDateTime;

public record AppointmentShowDTO(
        Long id,
        String clientFullName,
        String clientPhone,
        Long doctorId,
        String doctorFullName,
        LocalDateTime time,
        boolean beenBefore,
        String status,
        Integer price,
        String description
) {

}
