package ru.shmelev.stomatologyapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.stomatologyapp.domain.User;
import ru.shmelev.stomatologyapp.dto.RequestAppointmentCreate;
import ru.shmelev.stomatologyapp.enums.AppointmentStatus;
import ru.shmelev.stomatologyapp.security.CustomUserDetails;
import ru.shmelev.stomatologyapp.service.AppointmentService;
import ru.shmelev.stomatologyapp.service.DoctorService;

@Controller
@RequestMapping("/appointments")
@EnableMethodSecurity
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final DoctorService doctorService;

    @GetMapping
    public String listPage(Model model,
                       @AuthenticationPrincipal CustomUserDetails user) {

        model.addAttribute("scheduledAppointments", appointmentService.findAll(user, AppointmentStatus.SCHEDULED));
        model.addAttribute("doneAppointments", appointmentService.findAll(user,  AppointmentStatus.DONE));
        return "appointments/index";
    }

    @GetMapping("/{id}")
    public String appointmentPage(Model model, @PathVariable Long id) {

        model.addAttribute("appointment", appointmentService.showAppointment(id));

        return "appointments/show";
    }

    @GetMapping("/new")
    public String createPage(Model model) {
        model.addAttribute("appointment", new RequestAppointmentCreate());
        model.addAttribute("doctors", doctorService.findAllDoctors());
        return "appointments/new";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public String deleteAppointmentById(@PathVariable Long id) {
        appointmentService.deleteById(id);
        return "redirect:/appointments";
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String create(
            @ModelAttribute("appointment") @Valid RequestAppointmentCreate request,
            BindingResult bindingResult,
            @AuthenticationPrincipal CustomUserDetails userDetails,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("doctors", doctorService.findAllDoctors());
            return "appointments/new";
        }

        if (userDetails == null) {
            throw new RuntimeException("Unauthorized");
        }

        try {
            User currentUser = userDetails.getUser();
            appointmentService.create(request, currentUser);
        } catch (RuntimeException e) {
            bindingResult.reject("error.global", e.getMessage());
            model.addAttribute("doctors", doctorService.findAllDoctors());
            return "appointments/new";
        }

        return "redirect:/appointments";
    }

    @PostMapping("/done")
    @PreAuthorize("hasRole('ADMIN')")
    public String setDoneAppointment(@RequestParam Long appointmentId) {
        appointmentService.setStatus(appointmentId, AppointmentStatus.DONE);
        return "redirect:/appointments";
    }

}