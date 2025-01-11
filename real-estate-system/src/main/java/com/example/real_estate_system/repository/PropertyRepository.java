package com.example.real_estate_system.repository;

import com.example.real_estate_system.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    // Custom queries αν χρειαστούν
}
