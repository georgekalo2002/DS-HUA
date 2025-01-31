package com.example.real_estate_system.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role; // Ο ρόλος του χρήστη

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ViewingRequest> viewingRequests; // Οι αιτήσεις προβολής

    @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RentalRequest> rentalRequests; // Οι αιτήσεις ενοικίασης

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Property> properties; // Τα ακίνητα του ιδιοκτήτη

    // Getters and Setters
    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }

    public void setRole(Role role) { this.role = role; }

    public Set<ViewingRequest> getViewingRequests() { return viewingRequests; }

    public void setViewingRequests(Set<ViewingRequest> viewingRequests) { this.viewingRequests = viewingRequests; }

    public Set<RentalRequest> getRentalRequests() { return rentalRequests; }

    public void setRentalRequests(Set<RentalRequest> rentalRequests) { this.rentalRequests = rentalRequests; }

    public Set<Property> getProperties() { return properties; }

    public void setProperties(Set<Property> properties) { this.properties = properties; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", role=" + (role != null ? role.getName() : "null") +
                '}';
    }
}
