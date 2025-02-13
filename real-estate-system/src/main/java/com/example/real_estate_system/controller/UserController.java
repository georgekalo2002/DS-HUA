package com.example.real_estate_system.controller;

import com.example.real_estate_system.entity.User;
import com.example.real_estate_system.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller // Αντί για @RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String getAllUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user"; // Θα φορτώσει το templates/users.html
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard"; // Θα δείχνει σε templates/admin/dashboard.html
    }

    @Controller
    @RequestMapping("/owner")
    public class OwnerController {

        @GetMapping("/dashboard")
        public String dashboard(Model model) {
            return "owner/dashboard"; // Αναμένει το template owner/dashboard.html στο resources/templates
        }

        @GetMapping("/properties")
        public String properties(Model model) {
            return "owner/properties"; // Αναμένει το template owner/dashboard.html στο resources/templates
        }

        @GetMapping("/view-requests")
        public String viewrequests(Model model) {
            return "owner/viewrequests"; // Αναμένει το template owner/dashboard.html στο resources/templates
        }

        @GetMapping("/rental-requests")
        public String rentalrequests(Model model) {
            return "owner/rentalrequests"; // Αναμένει το template owner/dashboard.html στο resources/templates
        }

        @GetMapping("/create-property")
        public String createproperty(Model model) {
            return "owner/createproperty"; // Αναμένει το template owner/dashboard.html στο resources/templates
        }



    }
    @GetMapping("/tenant/dashboard")
    public String tenantDashboard() {
        return "tenant/dashboard"; // Θα δείχνει σε templates/tenant/dashboard.html
    }

    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal User user) {
        // Εδώ μπορείς να εμφανίσεις το username και άλλες πληροφορίες του χρήστη
        return "user/profile";
    }
}
