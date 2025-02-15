package com.example.real_estate_system.controller;

import com.example.real_estate_system.entity.User;
import com.example.real_estate_system.entity.UserRole;
import com.example.real_estate_system.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Επιστροφή όλων των χρηστών εκτός των ADMINS
    @GetMapping("/members")
    public ResponseEntity<List<User>> getAllMembers(@AuthenticationPrincipal UserDetails adminUser) {
        Optional<User> admin = userRepository.findByUsername(adminUser.getUsername());
        if (admin.isEmpty() || admin.get().getRole() != UserRole.ADMIN) {
            logger.warn("⛔ Μη εξουσιοδοτημένη πρόσβαση στα μέλη από: {}", adminUser.getUsername());
            return ResponseEntity.status(403).build();
        }

        List<User> members = userRepository.findByRoleNot(UserRole.ADMIN);
        logger.info("🔹 Φόρτωση {} χρηστών (TENANTS & OWNERS) για Admin: {}", members.size(), adminUser.getUsername());
        return ResponseEntity.ok(members);
    }

    // ✅ Δημιουργία νέου χρήστη (μόνο από Admin)
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@AuthenticationPrincipal UserDetails adminUser, @RequestBody User request) {
        logger.info("🔹 Λήφθηκε αίτημα δημιουργίας χρήστη: {}", request.getUsername());

        Optional<User> admin = userRepository.findByUsername(adminUser.getUsername());
        if (admin.isEmpty() || admin.get().getRole() != UserRole.ADMIN) {
            logger.warn("⛔ Μη εξουσιοδοτημένη δημιουργία χρήστη από: {}", adminUser.getUsername());
            return ResponseEntity.status(403).body("{\"error\": \"Μόνο ο Admin μπορεί να δημιουργήσει χρήστες!\"}");
        }

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            logger.warn("⚠️ Το username {} υπάρχει ήδη!", request.getUsername());
            return ResponseEntity.status(400).body("{\"error\": \"Το όνομα χρήστη υπάρχει ήδη!\"}");
        }

        // ✅ Μετατροπή String σε ENUM
        UserRole role;
        try {
            role = UserRole.valueOf(request.getRole().toString());
            logger.info("✅ Ρόλος μετατράπηκε επιτυχώς: {}", role);
        } catch (IllegalArgumentException e) {
            logger.error("⛔ Άκυρος ρόλος: {}", request.getRole(), e);
            return ResponseEntity.badRequest().body("{\"error\": \"Άκυρος ρόλος! Επιτρέπονται μόνο OWNER ή TENANT.\"}");
        }

        User newUser = new User();
        newUser.setUsername(request.getUsername());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(role);

        userRepository.save(newUser);
        logger.info("✅ Ο χρήστης {} δημιουργήθηκε με ρόλο {}", newUser.getUsername(), newUser.getRole());

        return ResponseEntity.ok("{\"success\": \"Ο χρήστης δημιουργήθηκε επιτυχώς!\"}");
    }

    // ✅ Διαγραφή χρήστη (TENANT ή OWNER μόνο)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetails adminUser, @PathVariable Long id) {
        Optional<User> admin = userRepository.findByUsername(adminUser.getUsername());
        if (admin.isEmpty() || admin.get().getRole() != UserRole.ADMIN) {
            logger.warn("⛔ Μη εξουσιοδοτημένη διαγραφή χρήστη από: {}", adminUser.getUsername());
            return ResponseEntity.status(403).body("{\"error\": \"Μόνο ο Admin μπορεί να διαγράψει χρήστες!\"}");
        }

        Optional<User> userToDelete = userRepository.findById(id);
        if (userToDelete.isEmpty()) {
            logger.warn("⚠️ Προσπάθεια διαγραφής ανύπαρκτου χρήστη με ID: {}", id);
            return ResponseEntity.status(400).body("{\"error\": \"Ο χρήστης δεν βρέθηκε!\"}");
        }

        if (userToDelete.get().getRole() == UserRole.ADMIN) {
            logger.warn("⚠️ Ο Admin προσπάθησε να διαγράψει Admin!");
            return ResponseEntity.status(400).body("{\"error\": \"Ο Admin δεν μπορεί να διαγράψει άλλους Admins!\"}");
        }

        userRepository.deleteById(id);
        logger.info("✅ Ο χρήστης {} διαγράφηκε από τον Admin {}", userToDelete.get().getUsername(), adminUser.getUsername());
        return ResponseEntity.ok("{\"success\": \"Ο χρήστης διαγράφηκε επιτυχώς!\"}");
    }
}
