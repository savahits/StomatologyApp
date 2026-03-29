package ru.shmelev.stomatologyapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shmelev.stomatologyapp.validator.ValidPhone;

@NoArgsConstructor
@Getter
@Setter
public class RequestDoctorCreate {

    @NotBlank(message = "Логин обязателен")
    private String username;

    @NotBlank(message = "Фамилия обязательна")
    private String surname;

    @NotBlank(message = "Имя обязательно")
    private String name;

    private String patronymic;

    @ValidPhone
    @NotBlank(message = "Номер телефона обязателен")
    private String phone;

    @NotNull(message = "Выберите специализацию")
    private Long specializationId;

    @NotBlank(message = "Пароль обязателен")
    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    private String password;
}