package com.example.real_estate_system.controller;

import com.example.real_estate_system.entity.*;
import com.example.real_estate_system.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/viewing-requests")
public class ViewingRequestController {

    private final ViewingRequestRepository viewingRequestRepository;
    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public ViewingRequestController(ViewingRequestRepository viewingRequestRepository, PropertyRepository propertyRepository, UserRepository userRepository) {
        this.viewingRequestRepository = viewingRequestRepository;
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveViewingRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(403).body("Χρήστης δεν βρέθηκε!");
        }

        Optional<ViewingRequest> optionalRequest = viewingRequestRepository.findById(id);
        if (optionalRequest.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ViewingRequest request = optionalRequest.get();
        User owner = request.getProperty().getOwner();

        // ✅ Επιτρέπεται μόνο στον OWNER του ακινήτου ή στον ADMIN
        if (!currentUser.get().equals(owner) && !currentUser.get().getRole().name().equals("ADMIN")) {
            return ResponseEntity.status(403).body("Δεν έχετε δικαίωμα να εγκρίνετε αυτή την αίτηση!");
        }

        request.setStatus(ViewingRequestStatus.APPROVED);
        viewingRequestRepository.save(request);
        return ResponseEntity.ok("Viewing request approved");
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectViewingRequest(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(403).body("Χρήστης δεν βρέθηκε!");
        }

        Optional<ViewingRequest> optionalRequest = viewingRequestRepository.findById(id);
        if (optionalRequest.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ViewingRequest request = optionalRequest.get();
        User owner = request.getProperty().getOwner();

        if (!currentUser.get().equals(owner) && !currentUser.get().getRole().name().equals("ADMIN")) {
            return ResponseEntity.status(403).body("Δεν έχετε δικαίωμα να απορρίψετε αυτή την αίτηση!");
        }

        request.setStatus(ViewingRequestStatus.REJECTED);
        viewingRequestRepository.save(request);
        return ResponseEntity.ok("Viewing request rejected");
    }
}
