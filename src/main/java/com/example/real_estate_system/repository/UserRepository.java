package com.example.real_estate_system.repository;

import com.example.real_estate_system.entity.User;
import com.example.real_estate_system.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    // ✅ Βρίσκει όλους τους χρήστες που ΔΕΝ είναι ADMIN
    @Query("SELECT u FROM User u WHERE u.role <> :role")
    List<User> findByRoleNot(@Param("role") UserRole role);
}
