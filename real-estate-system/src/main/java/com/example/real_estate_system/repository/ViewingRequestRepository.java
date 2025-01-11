package com.example.real_estate_system.repository;

import com.example.real_estate_system.entity.ViewingRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewingRequestRepository extends JpaRepository<ViewingRequest, Long> {
}
