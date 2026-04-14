package ru.shmelev.stomatologyapp.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.shmelev.stomatologyapp.domain.Role;
import ru.shmelev.stomatologyapp.repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {

        createRoleIfNotExists("ROLE_ADMIN");
        createRoleIfNotExists("ROLE_DOCTOR");
    }

    private void createRoleIfNotExists(String roleName) {
        Role role = roleRepository.findByName(roleName);

        if (role == null) {
            roleRepository.save(new Role(roleName));
        }
    }
}