package ru.shmelev.stomatologyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shmelev.stomatologyapp.domain.Appointment;
import ru.shmelev.stomatologyapp.domain.Client;
import ru.shmelev.stomatologyapp.domain.Doctor;
import ru.shmelev.stomatologyapp.domain.User;
import ru.shmelev.stomatologyapp.dto.AppointmentListItem;
import ru.shmelev.stomatologyapp.dto.AppointmentShowDTO;
import ru.shmelev.stomatologyapp.dto.RequestAppointmentCreate;
import ru.shmelev.stomatologyapp.dto.RequestClientCreate;
import ru.shmelev.stomatologyapp.enums.AppointmentStatus;
import ru.shmelev.stomatologyapp.exception.AppointmentAlreadyExistsException;
import ru.shmelev.stomatologyapp.repository.AppointmentRepository;
import ru.shmelev.stomatologyapp.repository.DoctorRepository;
import ru.shmelev.stomatologyapp.security.CustomUserDetails;
import ru.shmelev.stomatologyapp.utils.PhoneUtils;

import java.util.List;
import java.util.Optional;

@Service
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

    public AppointmentShowDTO showAppointment(Long appointmentId) {

        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);

        if (appointment.isEmpty()) {
            throw new RuntimeException("Appointment not found");
        }

        return new AppointmentShowDTO(
                appointment.get().getId(),
                appointment.get().getClient().getSurname() + " " + appointment.get().getClient().getName(),
                appointment.get().getClient().getPhone(),
                appointment.get().getDoctor().getId(),
                appointment.get().getDoctor().getSurname() + " " + appointment.get().getDoctor().getName(),
                appointment.get().getAppointmentTime(),
                appointment.get().getBeenBefore(),
                appointment.get().getStatus().name(),
                appointment.get().getPrice(),
                appointment.get().getDescription()
        );

    }

    @Transactional
    public void create(RequestAppointmentCreate request, User currentUser) {

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

        boolean beenBefore = clientService.existsByPhone(normalizedPhone);

        Client client = clientService.getOrCreate(clientDto);

        Doctor doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(request.time());
        appointment.setCreatedBy(currentUser);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setDescription(request.description());
        appointment.setBeenBefore(beenBefore);
        appointment.setPrice(request.price());

        try {
            appointmentRepository.save(appointment);
        } catch (DataIntegrityViolationException e) {
            throw new AppointmentAlreadyExistsException(
                    request.time(),
                    request.doctorId()
            );
        }
    }

    public List<AppointmentListItem> findAll(CustomUserDetails currentUser, AppointmentStatus status) {

        if (currentUser.hasRole("ROLE_ADMIN")) {
            return map(appointmentRepository.findAllWithClientAndDoctor(status));
        }

        if (currentUser.hasRole("ROLE_DOCTOR")) {

            Long doctorId = currentUser.getDoctorId();
            if (doctorId == null) {
                throw new IllegalStateException("User has ROLE_DOCTOR but no doctor linked");
            }

            return map(appointmentRepository.findAllByDoctorId(doctorId,  status));
        }

        throw new org.springframework.security.access.AccessDeniedException("Access denied");
    }

    @Transactional
    public void setStatus(Long appointmentId, AppointmentStatus status) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        if (appointment.getStatus() == AppointmentStatus.DONE) {
            return;
        }
        appointment.setStatus(status);

    }

    private List<AppointmentListItem> map(List<Appointment> appointments) {
        return appointments.stream()
                .map(a -> new AppointmentListItem(
                        a.getId(),
                        a.getClient().getSurname() + " " + a.getClient().getName(),
                        a.getClient().getPhone(),
                        a.getDoctor().getId(),
                        a.getDoctor().getSurname() + " " + a.getDoctor().getName(),
                        a.getAppointmentTime(),
                        a.getBeenBefore(),
                        a.getStatus().name()
                ))
                .toList();
    }

}
