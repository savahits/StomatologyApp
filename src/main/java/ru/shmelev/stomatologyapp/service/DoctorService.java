package ru.shmelev.stomatologyapp.service;

import ru.shmelev.stomatologyapp.domain.Doctor;
import java.util.List;

public interface DoctorService {

    List<Doctor> findAllDoctors();

}