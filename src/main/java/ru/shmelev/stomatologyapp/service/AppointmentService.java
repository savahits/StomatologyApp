package ru.shmelev.stomatologyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.shmelev.stomatologyapp.domain.Appointment;
import ru.shmelev.stomatologyapp.domain.Client;
import ru.shmelev.stomatologyapp.domain.Doctor;
import ru.shmelev.stomatologyapp.domain.User;
import ru.shmelev.stomatologyapp.dto.RequestAppointmentCreate;
import ru.shmelev.stomatologyapp.dto.RequestClientCreate;
import ru.shmelev.stomatologyapp.enums.AppointmentStatus;
import ru.shmelev.stomatologyapp.repository.AppointmentRepository;
import ru.shmelev.stomatologyapp.repository.DoctorRepository;

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

    @Transactional
    public void create(RequestAppointmentCreate request, User currentUser) {
        
        RequestClientCreate clientDto = new RequestClientCreate();
        clientDto.setName(request.getName());
        clientDto.setSurname(request.getSurname());
        clientDto.setPatronymic(request.getPatronymic());
        clientDto.setPhone(request.getPhone());

        Client client = clientService.getOrCreate(clientDto);

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        Appointment appointment = new Appointment();
        appointment.setClient(client);
        appointment.setDoctor(doctor);
        appointment.setAppointmentTime(request.getTime());
        appointment.setCreatedBy(currentUser);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        appointmentRepository.save(appointment);
    }
}
