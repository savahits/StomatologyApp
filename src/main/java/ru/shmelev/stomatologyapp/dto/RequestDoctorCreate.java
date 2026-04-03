package ru.shmelev.stomatologyapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import ru.shmelev.stomatologyapp.validator.ValidPhone;

public record RequestDoctorCreate(
        @NotBlank(message = "Логин обязателен")
        String username,

        @NotBlank(message = "Фамилия обязательна")
        String surname,

        @NotBlank(message = "Имя обязательно")
        String name,

        String patronymic,

        @ValidPhone
        @NotBlank(message = "Номер телефона обязателен")
        String phone,

        @NotNull(message = "Выберите специализацию")
        Long specializationId,

        @NotBlank(message = "Пароль обязателен")
        @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
        String password
) {
    public RequestDoctorCreate() {
        this(null, null, null, null, null, null, null);
    }
}
