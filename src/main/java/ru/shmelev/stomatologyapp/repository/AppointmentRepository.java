package ru.shmelev.stomatologyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shmelev.stomatologyapp.domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
