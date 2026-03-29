package ru.shmelev.stomatologyapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.shmelev.stomatologyapp.validator.ValidPhone;

import java.time.LocalDateTime;

@Getter
@Setter
public class RequestAppointmentCreate {

    @NotBlank(message = "Фамилия обязательна")
    @Size(max = 100, message = "Фамилия слишком длинная")
    private String surname;

    @NotBlank(message = "Имя обязательно")
    @Size(max = 100, message = "Имя слишком длинное")
    private String name;

    @Size(max = 100, message = "Отчество слишком длинная")
    private String patronymic;

    @NotBlank(message = "Номер телефона обязателен")
    @ValidPhone
    private String phone;

    @NotNull(message = "Выберете специалиста для приема")
    private Long doctorId;

    @NotNull(message = "Введите дату приема")
    private LocalDateTime time;

    @NotBlank(message = "Опишите прием пациента")
    private String description;
}
