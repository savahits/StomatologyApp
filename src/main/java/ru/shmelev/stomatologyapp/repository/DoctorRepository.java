package ru.shmelev.stomatologyapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.shmelev.stomatologyapp.domain.Doctor;
import ru.shmelev.stomatologyapp.dto.doctor.DoctorShowDTOForDoctors;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    @Query("""
        SELECT d FROM Doctor d
        JOIN FETCH d.specialization
        JOIN FETCH d.user
    """)
    List<Doctor> findAllDoctors();

    @Query(value = """
        SELECT d FROM Doctor d
        JOIN FETCH d.specialization
        JOIN FETCH d.user
    """,
            countQuery = "SELECT COUNT(d) FROM Doctor d")
    Page<Doctor> findAllDoctors(Pageable pageable);

    @Query("""
    SELECT new ru.shmelev.stomatologyapp.dto.doctor.DoctorShowDTOForDoctors(
        d.id,
        CONCAT(d.name, ' ', d.surname, ' ', d.patronymic),
        d.phone,
        s.name
    )
    FROM Doctor d
    JOIN d.specialization s
    WHERE d.id = :id
""")
    Optional<DoctorShowDTOForDoctors> findDoctorDto(Long id);
}