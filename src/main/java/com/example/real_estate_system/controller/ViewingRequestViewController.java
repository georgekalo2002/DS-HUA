package com.example.real_estate_system.controller;

import com.example.real_estate_system.entity.ViewingRequest;
import com.example.real_estate_system.repository.ViewingRequestRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewingRequestViewController {

    private final ViewingRequestRepository viewingRequestRepository;

    public ViewingRequestViewController(ViewingRequestRepository viewingRequestRepository) {
        this.viewingRequestRepository = viewingRequestRepository;
    }

    @GetMapping("/viewing-requests")
    public String showViewingRequests(Model model) {
        List<ViewingRequest> viewingRequests = viewingRequestRepository.findAll();
        model.addAttribute("viewingRequests", viewingRequests);
        return "ViewingRequest"; // Επιστρέφει το ViewingRequest.html
    }
}
