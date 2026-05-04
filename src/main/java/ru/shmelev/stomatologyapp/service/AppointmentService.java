package ru.shmelev.stomatologyapp.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shmelev.stomatologyapp.domain.Appointment;
import ru.shmelev.stomatologyapp.domain.Client;
import ru.shmelev.stomatologyapp.domain.Doctor;
import ru.shmelev.stomatologyapp.domain.User;
import ru.shmelev.stomatologyapp.dto.appointment.AppointmentListItem;
import ru.shmelev.stomatologyapp.dto.appointment.AppointmentShowDTO;
import ru.shmelev.stomatologyapp.dto.appointment.RequestAppointmentCreate;
import ru.shmelev.stomatologyapp.dto.RequestClientCreate;
import ru.shmelev.stomatologyapp.enums.AppointmentStatus;
import ru.shmelev.stomatologyapp.exception.AppointmentAlreadyExistsException;
import ru.shmelev.stomatologyapp.exception.NotFoundException;
import ru.shmelev.stomatologyapp.repository.AppointmentRepository;
import ru.shmelev.stomatologyapp.repository.DoctorRepository;
import ru.shmelev.stomatologyapp.security.CustomUserDetails;
import ru.shmelev.stomatologyapp.utils.PhoneUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Slf4j
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final ClientService clientService;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository,  DoctorRepository doctorRepository, ClientService clientService) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.clientService = clientService;
    }

    public AppointmentShowDTO showAppointment(Long appointmentId, CustomUserDetails currentUser) {

        Optional<Appointment> appointmentOpt = Optional.ofNullable(appointmentRepository.findAppointmentById(appointmentId));

        if (appointmentOpt.isEmpty()) {
            throw new NotFoundException("Appointment not found", appointmentId);
        }

        Appointment appointment = appointmentOpt.get();

        if (currentUser.hasRole("ROLE_DOCTOR")) {
            Long doctorId = currentUser.getDoctorId();
            if (doctorId == null || !appointment.getDoctor().getId().equals(doctorId)) {
                throw new org.springframework.security.access.AccessDeniedException("Access denied");
            }
        }

        return new AppointmentShowDTO(
                appointment.getId(),
                appointment.getClient().getSurname() + " " + appointment.getClient().getName(),
                appointment.getClient().getPhone(),
                appointment.getDoctor().getId(),
                appointment.getDoctor().getSurname() + " " + appointment.getDoctor().getName() + " " +  appointment.getDoctor().getPatronymic(),
                appointment.getAppointmentTime(),
                appointment.getIsNotFirstVisit(),
                appointment.getStatus().name(),
                appointment.getPrice(),
                appointment.getDescription()
        );

    }

    @Transactional
    public void createAppointment(RequestAppointmentCreate request, User currentUser) {

        if (appointmentRepository.existsByAppointmentTimeAndDoctorId(request.time(), request.doctorId())) {
            throw new AppointmentAlreadyExistsException(request.time(),  request.doctorId());
        }

        String normalizedPhone = null;
        if (request.phone() != null && !request.phone().isBlank()) {
            normalizedPhone = PhoneUtils.normalize(request.phone());
        }

        RequestClientCreate clientDto = new RequestClientCreate(
                request.surname(),
                request.name(),
                request.patronymic(),
                normalizedPhone
        );

        boolean beenBefore = clientService.isPhoneAlreadyRegistered(normalizedPhone);

        Client client = clientService.getOrCreateClient(clientDto);

        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new NotFoundException("Doctor not found", request.doctorId()));

        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(request.time());
        appointment.setCreatedBy(currentUser);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setDescription(request.description());
        appointment.setIsNotFirstVisit(beenBefore);
        appointment.setPrice(request.price());


        if (appointmentRepository.existsByAppointmentTimeAndDoctorId(request.time(), request.doctorId())) {
            throw new AppointmentAlreadyExistsException(request.time(),  request.doctorId());
        }

        appointmentRepository.save(appointment);
    }

    @PreAuthorize("hasAnyRole('ADMIN','DOCTOR')")
    public Page<AppointmentListItem> getAllAppointments(CustomUserDetails currentUser, AppointmentStatus status, Pageable pageable) {

        switch (currentUser.getRoleName()) {
            case "ROLE_DOCTOR":
                Long doctorId = currentUser.getDoctorId();
                if (doctorId == null) {
                    throw new IllegalStateException("Corrupted security context: doctorId is null");
                }
                return map(appointmentRepository.findAllByDoctorId(doctorId, status, pageable));

            case "ROLE_ADMIN":
                return map(appointmentRepository.findAll(status, pageable));

            default:
                throw new IllegalStateException("Unsupported role: " + currentUser.getRoleName());
        }
    }


    @Transactional
    public void setStatusToAppointment(Long appointmentId, AppointmentStatus status) {

        Appointment appointment = appointmentRepository.findAppointmentById(appointmentId);
        if  (appointment == null) {
            throw new NotFoundException("Appointment not found", appointmentId);
        }

        if (appointment.getStatus() == AppointmentStatus.DONE) {
            return;
        }
        appointment.setStatus(status);
        log.info("Updated appointment status: {}, time: {}", appointment.getStatus().name(), appointment.getAppointmentTime());

    }

    public void deleteAppointment(Long appointmentId) {
        if (!appointmentRepository.existsById(appointmentId)) {
            throw new NotFoundException("Appointment not found", appointmentId);
        }
        appointmentRepository.deleteById(appointmentId);
    }

    private Page<AppointmentListItem> map(Page<Appointment> appointments) {
        return appointments.map(a -> {
            boolean lateMark = isAppointmentLate(a);
            return new AppointmentListItem(
                    a.getId(),
                    a.getClient().getSurname() + " " + a.getClient().getName(),
                    a.getClient().getPhone(),
                    a.getDoctor().getId(),
                    a.getDoctor().getSurname() + " " + a.getDoctor().getName() + " " +  a.getDoctor().getPatronymic(),
                    a.getAppointmentTime(),
                    a.getIsNotFirstVisit(),
                    a.getStatus().name(),
                    lateMark
            );
        });
    }

    private boolean isAppointmentLate(Appointment appointment) {
        // Только для статуса SCHEDULED
        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            return false;
        }

        // Текущее время должно быть больше чем время записи + 1 день (в 00:00)
        LocalDateTime appointmentDateTime = appointment.getAppointmentTime();
        LocalDateTime nextDayAtMidnight = appointmentDateTime.toLocalDate().plusDays(1).atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        return now.isAfter(nextDayAtMidnight);
    }

}
