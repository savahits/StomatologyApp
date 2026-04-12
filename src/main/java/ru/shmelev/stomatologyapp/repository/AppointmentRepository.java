package ru.shmelev.stomatologyapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.shmelev.stomatologyapp.domain.Appointment;
import ru.shmelev.stomatologyapp.enums.AppointmentStatus;

import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("select a from Appointment a " +
            "join fetch a.client " +
            "join fetch a.doctor d " +
            "where a.status = :status")
    Page<Appointment> findAll(@Param("status") AppointmentStatus status, Pageable pageable);


    @Query("""
    select a from Appointment a
    join fetch a.client
    join fetch a.doctor
    where a.doctor.id = :doctorId and a.status = :status
""")
    Page<Appointment> findAllByDoctorId(Long doctorId, @Param("status") AppointmentStatus status, Pageable pageable);

    boolean existsByAppointmentTimeAndDoctorId(LocalDateTime appointmentTime, Long doctorId);
}
