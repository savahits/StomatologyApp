package ru.shmelev.stomatologyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.shmelev.stomatologyapp.domain.Appointment;
import ru.shmelev.stomatologyapp.enums.AppointmentStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a " +
            "join fetch a.client " +
            "join fetch a.doctor d " +
            "where a.status = :status")
    List<Appointment> findAllWithClientAndDoctor(@Param("status") AppointmentStatus status);


    @Query("""
    select a from Appointment a
    join fetch a.client
    join fetch a.doctor
    where a.doctor.id = :doctorId
""")
    List<Appointment> findAllByDoctorId(Long doctorId);

    boolean existsByAppointmentTimeAndDoctorId(LocalDateTime appointmentTime, Long doctorId);
}
