package ru.shmelev.stomatologyapp.service;

import org.springframework.stereotype.Service;
import ru.shmelev.stomatologyapp.domain.Doctor;
import ru.shmelev.stomatologyapp.repository.DoctorRepository;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorServiceImpl(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }
}