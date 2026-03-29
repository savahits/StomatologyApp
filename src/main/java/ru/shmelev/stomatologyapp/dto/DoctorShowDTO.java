package ru.shmelev.stomatologyapp.dto;

public record DoctorShowDTO(
        Long id,
        String fullName,
        String phone,
        String specialization
) {

}
