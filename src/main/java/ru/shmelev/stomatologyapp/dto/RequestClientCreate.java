package ru.shmelev.stomatologyapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.shmelev.stomatologyapp.validator.ValidPhone;

@Getter
@Setter
public class RequestClientCreate {

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

}