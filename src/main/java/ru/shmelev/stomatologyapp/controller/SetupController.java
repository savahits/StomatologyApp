package ru.shmelev.stomatologyapp.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.shmelev.stomatologyapp.dto.RequestAdminCreate;
import ru.shmelev.stomatologyapp.service.AdminSetupService;

@Controller
@RequestMapping("/setup")
public class SetupController {

    private final AdminSetupService adminSetupService;

    public SetupController(AdminSetupService adminSetupService) {
        this.adminSetupService = adminSetupService;
    }

    @GetMapping("/admin")
    public String showSetupForm(Model model) {
        model.addAttribute("form", new RequestAdminCreate());
        return "setup/admin";
    }

    @PostMapping("/admin")
    public String createAdmin(
            @Valid @ModelAttribute("form") RequestAdminCreate form,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("form", form);
            return "setup/admin";
        }

        try {
            adminSetupService.createAdmin(form);
        } catch (RuntimeException e) {
            model.addAttribute("form", form);
            model.addAttribute("error", e.getMessage());
            return "setup/admin";
        }

        return "redirect:/login";
    }
}