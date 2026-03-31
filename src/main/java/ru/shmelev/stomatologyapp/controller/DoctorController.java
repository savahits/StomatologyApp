package ru.shmelev.stomatologyapp.controller;

import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.stomatologyapp.dto.RequestDoctorCreate;
import ru.shmelev.stomatologyapp.exception.UsernameAlreadyExistsException;
import ru.shmelev.stomatologyapp.repository.SpecializationRepository;
import ru.shmelev.stomatologyapp.service.DoctorService;

@Controller
@EnableMethodSecurity
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

    @GetMapping("/{id}")
    public String getDoctorById(@PathVariable Long id, Model model) {
        model.addAttribute("doctor", doctorService.findById(id));

        return "doctors/show";
    }

    @PostMapping("/{id}")
    public String deleteDoctorById(@PathVariable Long id) {
        doctorService.delete(id);
        return "redirect:/doctors";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("doctor", new RequestDoctorCreate());
        model.addAttribute("specializations", specializationRepository.findAll());
        return "doctors/new";
    }

    @PreAuthorize("hasRole('ADMIN')")
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