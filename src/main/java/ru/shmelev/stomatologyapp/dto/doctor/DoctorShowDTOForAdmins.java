package ru.shmelev.stomatologyapp.dto.doctor;

public record DoctorShowDTOForAdmins(
        Long id,
        String fullName,
        String phone,
        String specialization,
        String login
) {

}
