package com.example.real_estate_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import com.example.real_estate_system.entity.User;

@Controller
public class AdminDashboardController {

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(@AuthenticationPrincipal User currentUser, Model model) {
        if (currentUser == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", currentUser);
        return "admin/dashboard"; // Το Spring Boot θα φορτώσει το `templates/admin/dashboard.html`
    }
}
