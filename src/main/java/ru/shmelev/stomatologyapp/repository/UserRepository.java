package ru.shmelev.stomatologyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shmelev.stomatologyapp.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name);

    boolean existsByUsername(String username);
}
