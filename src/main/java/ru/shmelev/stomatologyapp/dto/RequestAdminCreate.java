package ru.shmelev.stomatologyapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RequestAdminCreate(
        @NotBlank(message = "Логин обязателен")
        String username,

        @NotBlank(message = "Пароль обязателен")
        @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
        String password
) {
    public RequestAdminCreate() {
        this("", "");
    }
}