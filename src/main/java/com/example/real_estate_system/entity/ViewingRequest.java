package com.example.real_estate_system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class ViewingRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "tenant_id", nullable = false)
    private User tenant; // Ο ενοικιαστής που έκανε την αίτηση

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property; // Το ακίνητο που αφορά η αίτηση

    @Column(nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime requestedDateTime; // Ζητούμενη ημερομηνία/ώρα προβολής

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ViewingRequestStatus status; // Κατάσταση της αίτησης

    @Column(length = 500)
    private String message; // Μήνυμα ή σχόλιο για την αίτηση

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getTenant() { return tenant; }
    public void setTenant(User tenant) { this.tenant = tenant; }

    public Property getProperty() { return property; }
    public void setProperty(Property property) { this.property = property; }

    public LocalDateTime getRequestedDateTime() { return requestedDateTime; }
    public void setRequestedDateTime(LocalDateTime requestedDateTime) { this.requestedDateTime = requestedDateTime; }

    public ViewingRequestStatus getStatus() { return status; }
    public void setStatus(ViewingRequestStatus status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public String toString() {
        return "ViewingRequest{" +
                "id=" + id +
                ", requestedDateTime=" + requestedDateTime +
                ", status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
