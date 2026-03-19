package ru.shmelev.stomatologyapp.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shmelev.stomatologyapp.domain.Doctor;
import ru.shmelev.stomatologyapp.domain.Role;
import ru.shmelev.stomatologyapp.domain.Specialization;
import ru.shmelev.stomatologyapp.domain.User;
import ru.shmelev.stomatologyapp.dto.RequestDoctorSave;
import ru.shmelev.stomatologyapp.repository.DoctorRepository;
import ru.shmelev.stomatologyapp.repository.RoleRepository;
import ru.shmelev.stomatologyapp.repository.SpecializationRepository;
import ru.shmelev.stomatologyapp.repository.UserRepository;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SpecializationRepository specializationRepository;
    private final PasswordEncoder passwordEncoder;

    public DoctorServiceImpl(DoctorRepository doctorRepository,  UserRepository userRepository,
                             RoleRepository roleRepository, SpecializationRepository specializationRepository,
                             PasswordEncoder passwordEncoder) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.specializationRepository = specializationRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }


    @Transactional
    public void create(RequestDoctorSave dto) {

        if (userRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        Specialization specialization = specializationRepository.findById(dto.getSpecializationId())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));


        Role role = roleRepository.findByName("ROLE_DOCTOR")
                .orElseThrow(() -> new RuntimeException("Role not found"));



        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(role);

        userRepository.save(user);


        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSurname(dto.getSurname());
        doctor.setName(dto.getName());
        doctor.setPatronymic(dto.getPatronymic());
        doctor.setPhone(dto.getPhone());
        doctor.setSpecialization(specialization);

        doctorRepository.save(doctor);
    }
}