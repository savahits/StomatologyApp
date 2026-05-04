package ru.shmelev.stomatologyapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.shmelev.stomatologyapp.domain.Specialization;
import ru.shmelev.stomatologyapp.dto.RequestSpecializationCreate;
import ru.shmelev.stomatologyapp.exception.ConflictException;
import ru.shmelev.stomatologyapp.repository.SpecializationRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecializationService {

    private final SpecializationRepository specializationRepository;

    @Autowired
    public SpecializationService(SpecializationRepository specializationRepository) {
        this.specializationRepository = specializationRepository;
    }

    public Specialization createSpecialization(RequestSpecializationCreate request) {

        String name = request.name().trim();

        Specialization specialization = new Specialization();
        specialization.setName(name);

        try {
            return specializationRepository.save(specialization);
        } catch (DataIntegrityViolationException ex) {
            throw new ConflictException("Специализация уже существует: " + name);
        }
    }

    public String getSpecializationsNames(){

        List<String> specializationNames = specializationRepository.findAll().stream()
                .map(Specialization::getName)
                .collect(Collectors.toList());

        return String.join(", ", specializationNames);

    }

}
