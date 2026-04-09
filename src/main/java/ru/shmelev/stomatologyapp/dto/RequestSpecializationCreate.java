package ru.shmelev.stomatologyapp.dto;

import jakarta.validation.constraints.NotBlank;

public record RequestSpecializationCreate(
        @NotBlank(message = "Введите название специализации")
        String name
) {
}
