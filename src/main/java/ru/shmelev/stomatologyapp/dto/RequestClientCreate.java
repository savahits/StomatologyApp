package ru.shmelev.stomatologyapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.shmelev.stomatologyapp.validator.ValidPhone;

public record RequestClientCreate(
        @NotBlank
        @Size(max = 100)
        String surname,

        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 100)
        String patronymic,

        @NotBlank
        @ValidPhone
        String phone
) {}
