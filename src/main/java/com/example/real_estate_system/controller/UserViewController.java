package com.example.real_estate_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {

    @GetMapping("/create-owner")
    public String showCreateOwnerForm() {
        return "create-owner"; // Φορτώνει τη φόρμα `create-owner.html`
    }

    @GetMapping("/create-tenant")
    public String showCreateTenantForm() {
        return "create-tenant"; // Φορτώνει τη φόρμα `create-tenant.html`
    }
}
