package com.example.real_estate_system.controller;

import com.example.real_estate_system.entity.Property;
import com.example.real_estate_system.entity.User;
import com.example.real_estate_system.repository.PropertyRepository;
import com.example.real_estate_system.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/properties")
public class PropertyController {

    private final PropertyRepository propertyRepository;
    private final UserRepository userRepository;

    public PropertyController(PropertyRepository propertyRepository, UserRepository userRepository) {
        this.propertyRepository = propertyRepository;
        this.userRepository = userRepository;
    }

    // ✅ Επιστρέφει όλα τα ακίνητα
    @GetMapping
    public ResponseEntity<List<Property>> getAllProperties() {
        return ResponseEntity.ok(propertyRepository.findAll());
    }

    // ✅ Δημιουργία νέου ακινήτου από OWNER ή ADMIN
    @PostMapping
    public ResponseEntity<?> createProperty(@AuthenticationPrincipal UserDetails userDetails, @RequestBody Property property) {
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Πρέπει να είστε συνδεδεμένος!");
        }

        property.setOwner(currentUser.get());
        Property savedProperty = propertyRepository.save(property);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProperty);
    }

    // ✅ Διαγραφή ακινήτου (ΜΟΝΟ από τον OWNER του ή τον ADMIN)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProperty(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long id) {
        Optional<User> currentUser = userRepository.findByUsername(userDetails.getUsername());
        if (currentUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Πρέπει να είστε συνδεδεμένος!");
        }

        Optional<Property> property = propertyRepository.findById(id);
        if (property.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Το ακίνητο δεν βρέθηκε!");
        }

        // ✅ Επιτρέπουμε διαγραφή ΜΟΝΟ αν ο χρήστης είναι ο OWNER ή ADMIN
        if (!property.get().getOwner().equals(currentUser.get()) && !currentUser.get().getRole().name().equals("ADMIN")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Δεν έχετε δικαίωμα να διαγράψετε αυτό το ακίνητο!");
        }

        propertyRepository.deleteById(id);
        return ResponseEntity.ok("Το ακίνητο διαγράφηκε επιτυχώς!");
    }
}
