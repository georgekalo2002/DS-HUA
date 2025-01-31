package com.example.real_estate_system.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class RentalRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant; // Ο ενοικιαστής που έκανε την αίτηση

    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property; // Το ακίνητο για το οποίο έγινε η αίτηση

    @Column(nullable = false)
    private LocalDate requestDate; // Ημερομηνία αίτησης

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RentalRequestStatus status; // Κατάσταση αίτησης (enum)

    @Column
    private String message;

    // Getters and Setters
    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() { return message; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getTenant() { return tenant; }

    public void setTenant(User tenant) { this.tenant = tenant; }

    public Property getProperty() { return property; }

    public void setProperty(Property property) { this.property = property; }

    public LocalDate getRequestDate() { return requestDate; }

    public void setRequestDate(LocalDate requestDate) {
        this.requestDate = (requestDate != null) ? requestDate : LocalDate.now();
    }

    public RentalRequestStatus getStatus() { return status; }

    public void setStatus(RentalRequestStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "RentalRequest{" +
                "id=" + id +
                ", tenant=" + tenant.getUsername() +
                ", property=" + property.getName() +
                ", requestDate=" + requestDate +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
