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

    @NotBlank
    @Size(max = 100)
    private String surname;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String patronymic;

    @NotBlank
    @ValidPhone
    private String phone;

    @NotNull
    private Long doctorId;

    @NotNull
    private LocalDateTime time;
}
