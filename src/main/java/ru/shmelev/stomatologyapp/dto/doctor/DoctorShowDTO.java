package ru.shmelev.stomatologyapp.dto.doctor;

public record DoctorShowDTO(
        Long id,
        String fullName,
        String phone,
        String specialization
) {

}
