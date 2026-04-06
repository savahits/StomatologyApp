package ru.shmelev.stomatologyapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.shmelev.stomatologyapp.domain.Doctor;
import ru.shmelev.stomatologyapp.dto.DoctorShowDTO;
import ru.shmelev.stomatologyapp.dto.RequestDoctorCreate;

import java.util.List;

public interface DoctorService {

    List<Doctor> findAllDoctors();

    Page<Doctor> findAllDoctors(Pageable pageable);

    void create(RequestDoctorCreate dto);

    DoctorShowDTO findById(Long id);

    void delete(Long id);
}