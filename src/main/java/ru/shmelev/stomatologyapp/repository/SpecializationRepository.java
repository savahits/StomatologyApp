package ru.shmelev.stomatologyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.shmelev.stomatologyapp.domain.Specialization;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
}
