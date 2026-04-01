package ru.shmelev.stomatologyapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class AppointmentListItem {

    private Long id;

    private String clientFullName;
    private String clientPhone;

    private Long doctorId;
    private String doctorFullName;

    private LocalDateTime time;

    private boolean beenBefore;

    private String status;
}