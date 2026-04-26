package ru.shmelev.stomatologyapp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.stomatologyapp.dto.RequestSpecializationCreate;
import ru.shmelev.stomatologyapp.service.SpecializationService;

import java.util.List;

@Controller
@RequestMapping("/specializations")
public class SpecializationController {

    SpecializationService specializationService;

    @Autowired
    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }

    @GetMapping("/new")
    public String createPage(Model model) {
        model.addAttribute("specialization", new RequestSpecializationCreate());
        model.addAttribute("specializations", specializationService.getSpecializationsNames());
        return "specializations/new";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public String createSpecialization(@Valid @ModelAttribute("specialization") RequestSpecializationCreate dto,
                                     BindingResult bindingResult,
                                     Model model) {
        try {
            specializationService.create(dto);
            return "redirect:/doctors";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("name", "duplicate", e.getMessage());
            model.addAttribute("specialization", dto);
            return "specializations/new";
        }
    }
}
