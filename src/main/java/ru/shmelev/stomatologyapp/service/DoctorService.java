package ru.shmelev.stomatologyapp.service;

import ru.shmelev.stomatologyapp.domain.Doctor;
import ru.shmelev.stomatologyapp.dto.RequestDoctorSave;

import java.util.List;

public interface DoctorService {

    List<Doctor> findAllDoctors();

    void create(RequestDoctorSave dto);
}