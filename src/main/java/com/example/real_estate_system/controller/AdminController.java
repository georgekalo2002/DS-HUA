package com.example.real_estate_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin") // ✅ Σωστό import για το @RequestMapping
public class AdminController {

    @GetMapping("/manage-users")
    public String manageUsersPage() {
        return "manage-users"; // Προβάλλει το `manage-users.html`
    }
}
