package ru.shmelev.stomatologyapp.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.shmelev.stomatologyapp.dto.RequestAdminCreate;
import ru.shmelev.stomatologyapp.repository.RoleRepository;
import ru.shmelev.stomatologyapp.repository.UserRepository;
import ru.shmelev.stomatologyapp.domain.User;

@Service
public class AdminSetupService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminSetupService (UserRepository userRepository,  PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public boolean isAdminExists(){
        return userRepository.existsByRole_Name("ROLE_ADMIN");
    }

    public void createAdmin(@Valid RequestAdminCreate request) {

        if (isAdminExists()) {
            throw new RuntimeException("Admin already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(roleRepository.findByName("ROLE_ADMIN"));

        userRepository.save(user);
    }
}
