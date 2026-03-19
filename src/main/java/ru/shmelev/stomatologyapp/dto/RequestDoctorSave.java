package ru.shmelev.stomatologyapp.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RequestDoctorSave {

    private String username;
    private String surname;
    private String name;
    private String patronymic;
    private String phone;
    private Long specializationId;
    private String password;
}