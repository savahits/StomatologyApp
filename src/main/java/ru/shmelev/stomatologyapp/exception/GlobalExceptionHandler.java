package ru.shmelev.stomatologyapp.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.shmelev.stomatologyapp.dto.appointment.RequestAppointmentCreate;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DoctorHasAppointmentsException.class)
    public String handleDoctorHasAppointments(DoctorHasAppointmentsException ex,
                                              RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/doctors";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException ex, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", ex.getMessage());
        return "redirect:/doctors";
    }

    @ExceptionHandler(AppointmentAlreadyExistsException.class)

        public String handle(AppointmentAlreadyExistsException ex, Model model) {

            model.addAttribute("errorMessage", ex.getMessage());

            model.addAttribute("appointment", new RequestAppointmentCreate());

            return "appointments/new";

        }
}