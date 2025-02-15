package com.example.real_estate_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    // GET mapping για το login page
    @GetMapping("/login")
    public String loginPage() {
        return "login";  // Επιστροφή στο login.html
    }
}
