package ru.shmelev.stomatologyapp.dto.appointment;

import java.time.LocalDateTime;

public record AppointmentListItem(
    Long id,
    String clientFullName,
    String clientPhone,
    Long doctorId,
    String doctorFullName,
    LocalDateTime time,
    boolean beenBefore,
    String status,
    boolean lateMark
) {
}