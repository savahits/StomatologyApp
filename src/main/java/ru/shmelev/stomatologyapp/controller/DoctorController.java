package ru.shmelev.stomatologyapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shmelev.stomatologyapp.service.DoctorService;

@Controller
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String getDoctorsPage(Model model) {

        model.addAttribute("doctors", doctorService.findAllDoctors());

        return "doctors/index";
    }
}