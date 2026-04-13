package ru.shmelev.stomatologyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.shmelev.stomatologyapp.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);

    boolean existsByUsername(String username);

    @Query("SELECT COUNT(r) > 0 FROM Role r WHERE r.name = :roleName")
    boolean existsByRoleName(@Param("roleName") String roleName);
}
