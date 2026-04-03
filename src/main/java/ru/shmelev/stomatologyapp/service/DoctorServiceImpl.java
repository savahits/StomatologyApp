package ru.shmelev.stomatologyapp.service;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shmelev.stomatologyapp.domain.Doctor;
import ru.shmelev.stomatologyapp.domain.Role;
import ru.shmelev.stomatologyapp.domain.Specialization;
import ru.shmelev.stomatologyapp.domain.User;
import ru.shmelev.stomatologyapp.dto.DoctorShowDTO;
import ru.shmelev.stomatologyapp.dto.RequestDoctorCreate;
import ru.shmelev.stomatologyapp.exception.UsernameAlreadyExistsException;
import ru.shmelev.stomatologyapp.repository.DoctorRepository;
import ru.shmelev.stomatologyapp.repository.RoleRepository;
import ru.shmelev.stomatologyapp.repository.SpecializationRepository;
import ru.shmelev.stomatologyapp.repository.UserRepository;
import ru.shmelev.stomatologyapp.utils.PhoneUtils;

import java.util.List;
import java.util.Optional;

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
    public void delete(Long id) {
        doctorRepository.deleteById(id);
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public DoctorShowDTO findById(Long id) {
        Optional<Doctor> doctor = doctorRepository.findById(id);

        if (doctor.isEmpty()) {
            throw new RuntimeException("Doctor not found");
        }

        String specializationName = doctor.get().getSpecialization().getName();
        String phone = doctor.get().getPhone();
        String fullName = doctor.get().getName() + " " + doctor.get().getSurname() + " " + doctor.get().getPatronymic();

        return new DoctorShowDTO(
                doctor.get().getId(),
                fullName,
                phone,
                specializationName
        );
    }

    @Override
    @Transactional
    public void create(RequestDoctorCreate dto) {
        if (userRepository.existsByUsername(dto.username())) {
            throw new UsernameAlreadyExistsException(dto.username());
        }

        Specialization specialization = specializationRepository.findById(dto.specializationId())
                .orElseThrow(() -> new RuntimeException("Specialization not found"));

        Role role = roleRepository.findByName("ROLE_DOCTOR")
                .orElseThrow(() -> new RuntimeException("Role not found"));

        String normalizedPhone = null;
        if (dto.phone() != null && !dto.phone().isBlank()) {
            normalizedPhone = PhoneUtils.normalize(dto.phone());
        }

        User user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRole(role);
        userRepository.save(user);

        Doctor doctor = new Doctor();
        doctor.setUser(user);
        doctor.setSurname(dto.surname());
        doctor.setName(dto.name());
        doctor.setPatronymic(dto.patronymic());
        doctor.setPhone(normalizedPhone);
        doctor.setSpecialization(specialization);

        doctorRepository.save(doctor);
    }
}