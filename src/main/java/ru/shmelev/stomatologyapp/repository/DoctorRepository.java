package ru.shmelev.stomatologyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shmelev.stomatologyapp.domain.Doctor;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
