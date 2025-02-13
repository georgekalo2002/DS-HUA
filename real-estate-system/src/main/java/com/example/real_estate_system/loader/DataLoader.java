package com.example.real_estate_system.loader;

import com.example.real_estate_system.entity.*;
import com.example.real_estate_system.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PropertyRepository propertyRepository;
    private final RoleRepository roleRepository;
    private final RentalRequestRepository rentalRequestRepository;
    private final ViewingRequestRepository viewingRequestRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(
        UserRepository userRepository,
        PropertyRepository propertyRepository,
        RoleRepository roleRepository,
        RentalRequestRepository rentalRequestRepository,
        ViewingRequestRepository viewingRequestRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.propertyRepository = propertyRepository;
        this.roleRepository = roleRepository;
        this.rentalRequestRepository = rentalRequestRepository;
        this.viewingRequestRepository = viewingRequestRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Καθαρισμός πινάκων πριν την προσθήκη νέων δεδομένων (για αποφυγή διπλών εγγραφών)
        viewingRequestRepository.deleteAll();
        rentalRequestRepository.deleteAll();
        propertyRepository.deleteAll();
        userRepository.deleteAll();
        roleRepository.deleteAll();

        System.out.println("Καθαρίστηκαν οι πίνακες. Προσθήκη νέων δεδομένων...");

        // Δημιουργία ρόλων
        Role adminRole = new Role();
        adminRole.setName("ADMIN");
        roleRepository.save(adminRole);

        Role ownerRole = new Role();
        ownerRole.setName("OWNER");
        roleRepository.save(ownerRole);

        Role tenantRole = new Role();
        tenantRole.setName("TENANT");
        roleRepository.save(tenantRole);

        // Δημιουργία χρηστών με κρυπτογραφημένους κωδικούς
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(passwordEncoder.encode("admin123")); // Hashing password
        admin.setRole(adminRole);
        userRepository.save(admin);

        User owner = new User();
        owner.setUsername("owner");
        owner.setPassword(passwordEncoder.encode("owner123"));
        owner.setRole(ownerRole);
        userRepository.save(owner);

        User tenant = new User();
        tenant.setUsername("tenant");
        tenant.setPassword(passwordEncoder.encode("tenant123"));
        tenant.setRole(tenantRole);
        userRepository.save(tenant);

        // Δημιουργία ακινήτων
        Property property1 = new Property();
        property1.setName("Luxury Apartment");
        property1.setLocation("Athens");
        property1.setPrice(1500.0);
        property1.setOwner(owner);
        propertyRepository.save(property1);

        Property property2 = new Property();
        property2.setName("Cozy House");
        property2.setLocation("Thessaloniki");
        property2.setPrice(1200.0);
        property2.setOwner(owner);
        propertyRepository.save(property2);

        // Δημιουργία αιτήσεων ενοικίασης
        RentalRequest rentalRequest = new RentalRequest();
        rentalRequest.setTenant(tenant);
        rentalRequest.setProperty(property1);
        rentalRequest.setRequestDate(LocalDate.now());
        rentalRequest.setStatus(RentalRequestStatus.PENDING);
        rentalRequest.setMessage("I would like to rent this property.");
        rentalRequestRepository.save(rentalRequest);

        // Δημιουργία αιτήσεων προβολής
        ViewingRequest viewingRequest = new ViewingRequest();
        viewingRequest.setTenant(tenant);
        viewingRequest.setProperty(property2);
        viewingRequest.setRequestedDateTime(LocalDateTime.now());
        viewingRequest.setStatus(ViewingRequestStatus.PENDING);
        viewingRequest.setMessage("I would like to schedule a viewing.");
        viewingRequestRepository.save(viewingRequest);

        System.out.println("Δεδομένα αποθηκεύτηκαν επιτυχώς στη βάση δεδομένων!");
    }
}
