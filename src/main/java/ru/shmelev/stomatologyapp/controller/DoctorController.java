package ru.shmelev.stomatologyapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shmelev.stomatologyapp.dto.RequestDoctorSave;
import ru.shmelev.stomatologyapp.repository.SpecializationRepository;
import ru.shmelev.stomatologyapp.service.DoctorService;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;
    private final SpecializationRepository specializationRepository;

    public DoctorController(DoctorService doctorService,  SpecializationRepository specializationRepository) {
        this.doctorService = doctorService;
        this.specializationRepository = specializationRepository;
    }

    @GetMapping
    public String getDoctorsPage(Model model) {

        model.addAttribute("doctors", doctorService.findAllDoctors());

        return "doctors/index";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("doctor", new RequestDoctorSave());
        model.addAttribute("specializations", specializationRepository.findAll());
        return "doctors/new";
    }

    @PostMapping
    public String createDoctor(@ModelAttribute RequestDoctorSave dto) {
        doctorService.create(dto);
        return "redirect:/doctors";
    }

}