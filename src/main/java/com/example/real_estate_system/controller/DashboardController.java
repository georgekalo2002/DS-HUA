package com.example.real_estate_system.controller;

import com.example.real_estate_system.entity.*;
import com.example.real_estate_system.repository.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class DashboardController {

    private final PropertyRepository propertyRepository;
    private final ViewingRequestRepository viewingRequestRepository;
    private final RentalRequestRepository rentalRequestRepository;
    private final UserRepository userRepository;

    public DashboardController(PropertyRepository propertyRepository, ViewingRequestRepository viewingRequestRepository,
                               RentalRequestRepository rentalRequestRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.viewingRequestRepository = viewingRequestRepository;
        this.rentalRequestRepository = rentalRequestRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/dashboard")
    public String showDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }

        // 🔹 Ανάκτηση του πλήρους αντικειμένου `User`
        Optional<User> userOptional = userRepository.findByUsername(userDetails.getUsername());
        if (userOptional.isEmpty()) {
            return "redirect:/login";
        }
        User currentUser = userOptional.get();

        // 🔹 Φέρνουμε τα ακίνητα ανάλογα με τον ρόλο του χρήστη
        List<Property> properties = getPropertiesByRole(currentUser);
        model.addAttribute("user", currentUser);
        model.addAttribute("role", currentUser.getRole().name());
        model.addAttribute("properties", properties);

        // 🔹 Αν είναι OWNER ή ADMIN, προσθέτουμε τις αιτήσεις Viewing & Rental
        if (currentUser.getRole() == UserRole.OWNER || currentUser.getRole() == UserRole.ADMIN) {
            List<ViewingRequest> viewingRequests = viewingRequestRepository.findAll();
            List<RentalRequest> rentalRequests = rentalRequestRepository.findAll();
            model.addAttribute("viewingRequests", viewingRequests);
            model.addAttribute("rentalRequests", rentalRequests);
        }

        return "dashboard";
    }

    private List<Property> getPropertiesByRole(User currentUser) {
        switch (currentUser.getRole()) {
            case OWNER:
                return propertyRepository.findByOwner(currentUser);
            case ADMIN:
                return propertyRepository.findAll();
            case TENANT:
                return propertyRepository.findAll();
            default:
                return List.of();
        }
    }
}
    