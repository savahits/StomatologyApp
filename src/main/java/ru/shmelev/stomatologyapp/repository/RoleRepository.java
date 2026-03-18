package ru.shmelev.stomatologyapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.shmelev.stomatologyapp.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
