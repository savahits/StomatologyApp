package ru.shmelev.stomatologyapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.shmelev.stomatologyapp.domain.Doctor;

import java.util.List;

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
        SELECT d FROM Doctor d
        JOIN FETCH d.specialization
        JOIN FETCH d.user
        WHERE d.id = :id
    """)
    Doctor findDoctorById(@Param("id") Long id);
}