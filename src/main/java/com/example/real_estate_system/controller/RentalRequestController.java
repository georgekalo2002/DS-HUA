package com.example.real_estate_system.controller;

import com.example.real_estate_system.entity.*;
import com.example.real_estate_system.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/rental-requests")
public class RentalRequestController {

    private final RentalRequestRepository rentalRequestRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public RentalRequestController(RentalRequestRepository rentalRequestRepository, PropertyRepository propertyRepository, UserRepository userRepository) {
        this.rentalRequestRepository = rentalRequestRepository;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveRentalRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(403).body("Χρήστης δεν βρέθηκε!");
        }

        Optional<RentalRequest> optionalRequest = rentalRequestRepository.findById(id);
        if (optionalRequest.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RentalRequest request = optionalRequest.get();
        User owner = request.getProperty().getOwner();

        if (!currentUser.get().equals(owner) && !currentUser.get().getRole().name().equals("ADMIN")) {
            return ResponseEntity.status(403).body("Δεν έχετε δικαίωμα να εγκρίνετε αυτή την αίτηση!");
        }

        request.setStatus(RentalRequestStatus.APPROVED);
        rentalRequestRepository.save(request);
        return ResponseEntity.ok("Rental request approved");
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectRentalRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(403).body("Χρήστης δεν βρέθηκε!");
        }

        Optional<RentalRequest> optionalRequest = rentalRequestRepository.findById(id);
        if (optionalRequest.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        RentalRequest request = optionalRequest.get();
        User owner = request.getProperty().getOwner();

        if (!currentUser.get().equals(owner) && !currentUser.get().getRole().name().equals("ADMIN")) {
            return ResponseEntity.status(403).body("Δεν έχετε δικαίωμα να απορρίψετε αυτή την αίτηση!");
        }

        request.setStatus(RentalRequestStatus.REJECTED);
        rentalRequestRepository.save(request);
        return ResponseEntity.ok("Rental request rejected");
    }
}
