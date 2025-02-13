package com.example.real_estate_system.controller;

import com.example.real_estate_system.entity.Property;
import com.example.real_estate_system.entity.User;
import com.example.real_estate_system.repository.PropertyRepository;
import com.example.real_estate_system.repository.UserRepository;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller  // Αντί για @RestController
@RequestMapping("/properties")
public class PropertyController {
    private UserRepository userRepository;
    private final PropertyRepository propertyRepository;

    public PropertyController(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }


    @GetMapping
    public String getAllProperties(Model model) {
        List<Property> properties = propertyRepository.findAll();
        model.addAttribute("properties", properties);
        return "property"; // Φορτώνει το templates/properties.html
    }

    @GetMapping("/create")
    public String showCreatePropertyForm(Model model) {
        return "create_property"; // Φορτώνει το templates/create_property.html
    }

    @PostMapping("/create")
    public String createProperty(Property property, @AuthenticationPrincipal User currentUser){
        if (currentUser == null) {
            return "login"; // Αν ο χρήστης δεν είναι συνδεδεμένος, προχωράμε στη σελίδα σύνδεσης
        }

        // Ανάκτηση του username και του ID του χρήστη
        String username = currentUser.getUsername();
        Long userId = currentUser.getId();
        // Εκτύπωση στην κονσόλα
        System.out.println("User '" + username + "' with ID '" + userId + "' is attempting to create a new property.");

        // Βρίσκουμε τον χρήστη από το ID
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Ορίζουμε τον ιδιοκτήτη (owner) του ακινήτου
        property.setOwner(owner);
        propertyRepository.save(property);

        // Εκτύπωση στην κονσόλα όταν η καταχώρηση αποθηκεύεται επιτυχώς
        System.out.println("Property created successfully by user '" + username + "' with ID '" + userId + "'.");

        return "redirect:/properties"; //
    }
}
