package com.example.real_estate_system.repository;

import com.example.real_estate_system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Αν χρειαστείς custom queries, τα προσθέτεις εδώ
}
