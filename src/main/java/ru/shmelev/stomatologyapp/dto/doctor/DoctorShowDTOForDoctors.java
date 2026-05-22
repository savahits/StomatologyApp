package ru.shmelev.stomatologyapp.dto.doctor;

public record DoctorShowDTOForDoctors(
        Long id,
        String fullName,
        String phone,
        String specialization
) {

}
