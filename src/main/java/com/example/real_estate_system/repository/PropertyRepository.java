package com.example.real_estate_system.repository;

import com.example.real_estate_system.entity.Property;
import com.example.real_estate_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {
    List<Property> findByOwner(User owner);
}
