package com.example.real_estate_system.controller;

import com.example.real_estate_system.entity.RentalRequest;
import com.example.real_estate_system.repository.RentalRequestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller // Αντί για @RestController
@RequestMapping("/rental-requests")
public class RentalRequestController {

    private final RentalRequestRepository rentalRequestRepository;

    public RentalRequestController(RentalRequestRepository rentalRequestRepository) {
        this.rentalRequestRepository = rentalRequestRepository;
    }

    @GetMapping
    public String getAllRentalRequests(Model model) {
        List<RentalRequest> rentalRequests = rentalRequestRepository.findAll();
        model.addAttribute("rentalRequests", rentalRequests);
        return "rentalRequest"; // Θα φορτώσει το templates/rental_requests.html
    }
}
