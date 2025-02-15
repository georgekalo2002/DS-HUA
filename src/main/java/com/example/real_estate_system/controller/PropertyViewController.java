package com.example.real_estate_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PropertyViewController {

    @GetMapping("/properties")
    public String showPropertiesPage() {
        return "properties"; // Φορτώνει το properties.html
    }

    @GetMapping("/create-property")
    public String showCreatePropertyForm() {
        return "createproperty"; // Φορτώνει το createproperty.html
    }
}
