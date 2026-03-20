package ru.shmelev.stomatologyapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

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
    private String phone;

}