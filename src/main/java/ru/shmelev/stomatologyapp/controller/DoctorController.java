package ru.shmelev.stomatologyapp.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shmelev.stomatologyapp.dto.RequestDoctorCreate;
import ru.shmelev.stomatologyapp.exception.UsernameAlreadyExistsException;
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
        model.addAttribute("doctor", new RequestDoctorCreate());
        model.addAttribute("specializations", specializationRepository.findAll());
        return "doctors/new";
    }


    @PostMapping
    public String createDoctor(
            @Valid @ModelAttribute("doctor") RequestDoctorCreate dto,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("specializations", specializationRepository.findAll());
            return "doctors/new";
        }

        try {
            doctorService.create(dto);
        } catch (UsernameAlreadyExistsException ex) {
            bindingResult.rejectValue("username", "", "Логин уже занят");
            model.addAttribute("specializations", specializationRepository.findAll());
            return "doctors/new";
        } catch (RuntimeException ex) {
            bindingResult.reject("error", "Что-то пошло не так");
            model.addAttribute("specializations", specializationRepository.findAll());
            return "doctors/new";
        }

        return "redirect:/doctors";
    }

}