package com.example.real_estate_system.controller;

import com.example.real_estate_system.entity.ViewingRequest;
import com.example.real_estate_system.repository.ViewingRequestRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viewing-requests")
public class ViewingRequestController {

    private final ViewingRequestRepository viewingRequestRepository;

    public ViewingRequestController(ViewingRequestRepository viewingRequestRepository) {
        this.viewingRequestRepository = viewingRequestRepository;
    }

    @GetMapping
    public List<ViewingRequest> getAllViewingRequests() {
        return viewingRequestRepository.findAll();
    }

    @GetMapping("/{id}")
    public ViewingRequest getViewingRequestById(@PathVariable Long id) {
        return viewingRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("ViewingRequest not found"));
    }

    @PostMapping
    public ViewingRequest createViewingRequest(@RequestBody ViewingRequest viewingRequest) {
        return viewingRequestRepository.save(viewingRequest);
    }

    @PutMapping("/{id}")
    public ViewingRequest updateViewingRequest(@PathVariable Long id, @RequestBody ViewingRequest requestDetails) {
        ViewingRequest viewingRequest = viewingRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("ViewingRequest not found"));
        viewingRequest.setStatus(requestDetails.getStatus());
        viewingRequest.setMessage(requestDetails.getMessage());
        return viewingRequestRepository.save(viewingRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteViewingRequest(@PathVariable Long id) {
        viewingRequestRepository.deleteById(id);
    }

    @PutMapping("/{id}/approve")
    public ViewingRequest approveRequest(@PathVariable Long id) {
        ViewingRequest request = viewingRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        request.setStatus("APPROVED");
        return viewingRequestRepository.save(request);
    }

}


