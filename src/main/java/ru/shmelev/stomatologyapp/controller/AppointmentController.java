package ru.shmelev.stomatologyapp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.shmelev.stomatologyapp.domain.User;
import ru.shmelev.stomatologyapp.dto.appointment.RequestAppointmentCreate;
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
    public String getAll(Model model,
                        @RequestParam(defaultValue = "0") int scheduledPage,
                        @RequestParam(defaultValue = "4") int scheduledSize,
                        @RequestParam(defaultValue = "0") int donePage,
                        @RequestParam(defaultValue = "4") int doneSize,
                        @AuthenticationPrincipal CustomUserDetails user) {

        Pageable scheduledPageable = PageRequest.of(scheduledPage, scheduledSize, Sort.by("id").descending());
        Pageable donePageable = PageRequest.of(donePage, doneSize, Sort.by("id").descending());


        model.addAttribute("scheduledAppointments", appointmentService.findAll(user, AppointmentStatus.SCHEDULED, scheduledPageable));
        model.addAttribute("doneAppointments", appointmentService.findAll(user, AppointmentStatus.DONE, donePageable));
        return "appointments/index";
    }

    @GetMapping("/{id}")
    public String getOne(Model model, @PathVariable Long id, @AuthenticationPrincipal CustomUserDetails user) {

        model.addAttribute("appointment", appointmentService.showAppointment(id, user));

        return "appointments/show";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("appointment", new RequestAppointmentCreate());
        model.addAttribute("doctors", doctorService.findAllDoctors());
        return "appointments/new";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}")
    public String delete(@PathVariable Long id) {
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