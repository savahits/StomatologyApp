package ru.shmelev.stomatologyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.shmelev.stomatologyapp.domain.Appointment;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("""
        select a from Appointment a
        join fetch a.client
        join fetch a.doctor d
    """)
    List<Appointment> findAllWithClientAndDoctor();
}
