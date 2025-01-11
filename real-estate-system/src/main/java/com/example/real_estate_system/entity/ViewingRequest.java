package com.example.real_estate_system.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class ViewingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant; // Ο ενοικιαστής που έκανε την αίτηση
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "property_id", nullable = false)
    private Property property; // Το ακίνητο που αφορά η αίτηση

    @Column(nullable = false)
    private LocalDateTime requestedDateTime; // Ζητούμενη ημερομηνία/ώρα προβολής

    @Column(nullable = false)
    private String status; // Κατάσταση (PENDING, APPROVED, REJECTED)

    @Column
    private String message;

    public String getMessage() {
    return message;
    }

    public void setMessage(String message) {
    this.message = message;
    }


    // Getters and Setters
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getTenant() {
        return tenant;
    }

    public void setTenant(User tenant) {
        this.tenant = tenant;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public LocalDateTime getRequestedDateTime() {
        return requestedDateTime;
    }

    public void setRequestedDateTime(LocalDateTime requestedDateTime) {
        this.requestedDateTime = requestedDateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
