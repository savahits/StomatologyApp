package ru.shmelev.stomatologyapp.dto.appointment;

import jakarta.validation.constraints.*;
import ru.shmelev.stomatologyapp.validator.ValidPhone;

import java.time.LocalDateTime;

public record RequestAppointmentCreate(
        @NotBlank(message = "Фамилия пациента обязательна")
        @Size(max = 100, message = "Фамилия слишком длинная")
        String surname,

        @NotBlank(message = "Имя пациента обязательно")
        @Size(max = 100, message = "Имя слишком длинное")
        String name,

        @Size(max = 100, message = "Отчество слишком длинное")
        String patronymic,

        @NotBlank(message = "Номер телефона пациента обязателен")
        @ValidPhone
        String phone,

        @NotNull(message = "Выберете специалиста для приема")
        Long doctorId,

        @NotNull(message = "Введите дату приема")
        @Future(message = "Некорректная дата приема")
        LocalDateTime time,

        @NotBlank(message = "Опишите прием пациента")
        String description,

        @NotNull(message = "Введите стоимость приема")
        @Min(value = 0, message = "Введите стоимость приема")
        Integer price
) {
    public RequestAppointmentCreate() {
        this(null, null, null, null, null, null, null, null);
    }
}
