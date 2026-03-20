package ru.shmelev.stomatologyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shmelev.stomatologyapp.domain.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Optional<Client> findByPhone(String phone);
}