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

    private String doctorFullName;

    private LocalDateTime time;

    private String status;
}