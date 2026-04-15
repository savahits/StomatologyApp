package ru.shmelev.stomatologyapp.config;

import jakarta.transaction.Transactional;
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
    @Transactional
    public void run(String... args) {

        System.out.println("Initializing roles...");
        createRoleIfNotExists("ROLE_ADMIN");
        createRoleIfNotExists("ROLE_DOCTOR");
    }

    private void createRoleIfNotExists(String roleName) {
        System.out.println(">>> INPUT ROLE: [" + roleName + "]");



        Role role = roleRepository.findByName(roleName);

        if (!roleRepository.existsByName(roleName)) {
            try {
                roleRepository.save(new Role(roleName));
            } catch (Exception e) {
                System.out.println("ROLE already exists: " + roleName);
            }
        }
    }
}