package ru.shmelev.stomatologyapp.service;

import ru.shmelev.stomatologyapp.domain.Doctor;
import ru.shmelev.stomatologyapp.dto.RequestDoctorCreate;

import java.util.List;

public interface DoctorService {

    List<Doctor> findAllDoctors();

    void create(RequestDoctorCreate dto);
}