package com.example.real_estate_system.controller;

import com.example.real_estate_system.entity.ViewingRequest;
import com.example.real_estate_system.repository.ViewingRequestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller // Αντί για @RestController
@RequestMapping("/viewing-requests")
public class ViewingRequestController {

    private final ViewingRequestRepository viewingRequestRepository;

    public ViewingRequestController(ViewingRequestRepository viewingRequestRepository) {
        this.viewingRequestRepository = viewingRequestRepository;
    }

    @GetMapping
    public String getAllViewingRequests(Model model) {
        List<ViewingRequest> viewingRequests = viewingRequestRepository.findAll();
        model.addAttribute("viewingRequests", viewingRequests);
        return "viewingRequest"; // Θα φορτώσει το templates/viewing_requests.html
    }
}
