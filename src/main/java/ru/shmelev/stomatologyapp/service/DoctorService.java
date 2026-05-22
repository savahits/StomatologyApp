package ru.shmelev.stomatologyapp.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shmelev.stomatologyapp.domain.Doctor;
import ru.shmelev.stomatologyapp.domain.Role;
import ru.shmelev.stomatologyapp.domain.Specialization;
import ru.shmelev.stomatologyapp.domain.User;
import ru.shmelev.stomatologyapp.dto.doctor.DoctorShowDTOForDoctors;
import ru.shmelev.stomatologyapp.dto.doctor.RequestDoctorCreate;
import ru.shmelev.stomatologyapp.exception.DoctorHasAppointmentsException;
import ru.shmelev.stomatologyapp.exception.NotFoundException;
import ru.shmelev.stomatologyapp.exception.UsernameAlreadyExistsException;
import ru.shmelev.stomatologyapp.repository.*;
import ru.shmelev.stomatologyapp.utils.PhoneUtils;

import java.util.List;

@Service
@Slf4j
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SpecializationRepository specializationRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppointmentRepository appointmentRepository;

    public DoctorService(DoctorRepository doctorRepository, UserRepository userRepository,
                         RoleRepository roleRepository, SpecializationRepository specializationRepository,
                         PasswordEncoder passwordEncoder,  AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.specializationRepository = specializationRepository;
        this.passwordEncoder = passwordEncoder;
        this.appointmentRepository = appointmentRepository;
    }

    @Transactional
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor", id));

        String fullName = doctor.getName() + " " + doctor.getSurname();
        User user = userRepository.findByDoctorId(id);
        String username = user != null ? user.getUsername() : "N/A";

        if (appointmentRepository.existsByDoctorId(id)) {
            throw new DoctorHasAppointmentsException(id);
        }

        doctorRepository.delete(doctor);
        if (user != null) {
            userRepository.delete(user);
        }

        log.info("Deleted doctor: {}, username: {}", fullName, username);
    }



    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAllDoctors();
    }

    public Page<Doctor> getAllDoctors(Pageable pageable) {
        return doctorRepository.findAllDoctors(pageable);
    }

    public DoctorShowDTOForDoctors findDoctor(Long id) {

        return doctorRepository.findDoctorDto(id)
                .orElseThrow(() -> new NotFoundException("Doctor", id));

    }

    @Transactional
    public void createDoctor(RequestDoctorCreate dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new UsernameAlreadyExistsException(dto.username());
        }

        Specialization specialization = specializationRepository.findById(dto.specializationId())
                .orElseThrow(() -> new NotFoundException("Specialization", dto.specializationId()));

        Role role = roleRepository.findByName("ROLE_DOCTOR")
                .orElseThrow(() -> new NotFoundException("Role", "ROLE_DOCTOR"));

        String normalizedPhone = null;
        if (dto.phone() != null && !dto.phone().isBlank()) {
            normalizedPhone = PhoneUtils.normalize(dto.phone());
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(role);
        userRepository.save(user);

        log.info("Created user with username {}", dto.username());

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSurname(dto.surname());
        doctor.setName(dto.name());
        doctor.setPatronymic(dto.patronymic());
        doctor.setPhone(normalizedPhone);
        doctor.setSpecialization(specialization);

        doctorRepository.save(doctor);

        log.info("Created doctor {}", dto.username() + " " + dto.surname() + " " + dto.phone());
    }

    public boolean noDoctorsExists() {
        return doctorRepository.count() == 0;
    }

}