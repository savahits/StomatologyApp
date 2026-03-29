package ru.shmelev.stomatologyapp.dto;

import ru.shmelev.stomatologyapp.domain.Specialization;

public record DoctorShowDTO(
        Long id,
        String fullName,
        String phone,
        Specialization specialization
) {

}
