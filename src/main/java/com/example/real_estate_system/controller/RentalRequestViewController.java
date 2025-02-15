package com.example.real_estate_system.controller;

import com.example.real_estate_system.entity.RentalRequest;
import com.example.real_estate_system.repository.RentalRequestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RentalRequestViewController {

    private final RentalRequestRepository rentalRequestRepository;

    public RentalRequestViewController(RentalRequestRepository rentalRequestRepository) {
        this.rentalRequestRepository = rentalRequestRepository;
    }

    @GetMapping("/rental-requests")
    public String showRentalRequests(Model model) {
        List<RentalRequest> rentalRequests = rentalRequestRepository.findAll();
        model.addAttribute("rentalRequests", rentalRequests);
        return "RentalRequest";
    }
}
