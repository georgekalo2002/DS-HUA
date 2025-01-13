package com.example.real_estate_system.controller;
import com.example.real_estate_system.entity.RentalRequest;
import com.example.real_estate_system.repository.RentalRequestRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
    @RestController
    @RequestMapping("/rental-requests")
    public class RentalRequestController {

        private final RentalRequestRepository rentalRequestRepository;

        public RentalRequestController(RentalRequestRepository rentalRequestRepository) {
            this.rentalRequestRepository = rentalRequestRepository;
        }

        @GetMapping
        public List<RentalRequest> getAllRentalRequests() {
            return rentalRequestRepository.findAll();
        }

        @GetMapping("/{id}")
        public RentalRequest getRentalRequestById(@PathVariable Long id) {
            return rentalRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("RentalRequest not found"));
        }

        @PostMapping
        public RentalRequest createRentalRequest(@RequestBody RentalRequest rentalRequest) {
            return rentalRequestRepository.save(rentalRequest);
        }

        @PutMapping("/{id}")
        public RentalRequest updateRentalRequest(@PathVariable Long id, @RequestBody RentalRequest requestDetails) {
            RentalRequest rentalRequest = rentalRequestRepository.findById(id).orElseThrow(() -> new RuntimeException("RentalRequest not found"));
            rentalRequest.setStatus(requestDetails.getStatus());
            rentalRequest.setMessage(requestDetails.getMessage());
            return rentalRequestRepository.save(rentalRequest);
        }

        @DeleteMapping("/{id}")
        public void deleteRentalRequest(@PathVariable Long id) {
            rentalRequestRepository.deleteById(id);
        }


    }


