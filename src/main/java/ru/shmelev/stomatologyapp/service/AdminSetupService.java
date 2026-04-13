package ru.shmelev.stomatologyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shmelev.stomatologyapp.repository.UserRepository;

@Service
public class AdminSetupService {

    private final UserRepository userRepository;

    @Autowired
    public AdminSetupService (UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public boolean isAdminExists(){
        return userRepository.existsAdmin("ROLE_ADMIN");
    }

}
