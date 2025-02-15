package com.example.real_estate_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.example.real_estate_system.entity.User;

@Controller
public class OwnerDashboardController {

    @GetMapping("/owner/dashboard")
    public String showOwnerDashboard(@AuthenticationPrincipal User currentUser, Model model) {
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", currentUser);
        return "owner/dashboard"; // Το Spring Boot θα φορτώσει το `templates/owner/dashboard.html`
    }
}
