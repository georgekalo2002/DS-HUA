package com.example.real_estate_system.repository;

import com.example.real_estate_system.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    // Custom method to check if a role exists by its name
    boolean existsByName(String name);
}
