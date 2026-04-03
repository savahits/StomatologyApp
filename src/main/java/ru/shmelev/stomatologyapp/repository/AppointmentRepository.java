package ru.shmelev.stomatologyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.shmelev.stomatologyapp.domain.Appointment;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
        select a from Appointment a
        join fetch a.client
        join fetch a.doctor d
    """)
    List<Appointment> findAllWithClientAndDoctor();

    @Query("""
    select a from Appointment a
    join fetch a.client
    join fetch a.doctor
    where a.doctor.id = :doctorId
""")
    List<Appointment> findAllByDoctorId(Long doctorId);

    boolean existsByAppointmentTimeAndDoctorId(LocalDateTime appointmentTime, Long doctorId);
}
