package ru.shmelev.stomatologyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.shmelev.stomatologyapp.domain.Specialization;
import ru.shmelev.stomatologyapp.dto.RequestSpecializationCreate;
import ru.shmelev.stomatologyapp.repository.SpecializationRepository;

@Service
public class SpecializationService {

    private SpecializationRepository specializationRepository;

    @Autowired
    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public Specialization create(RequestSpecializationCreate request) {
        Specialization specialization = new Specialization();
        specialization.setName(request.name());
        return specializationRepository.save(specialization);
    }

}
