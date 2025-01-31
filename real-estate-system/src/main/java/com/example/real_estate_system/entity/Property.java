package com.example.real_estate_system.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String location;

    private Double price;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner; // Ο ιδιοκτήτης του ακινήτου

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ViewingRequest> viewingRequests; // Οι αιτήσεις προβολής για το ακίνητο

    @OneToMany(mappedBy = "property", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RentalRequest> rentalRequests; // Αιτήσεις για το ακίνητο

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public User getOwner() { return owner; }
    public void setOwner(User owner) { this.owner = owner; }

    public Set<RentalRequest> getRentalRequests() { return rentalRequests; }
    public void setRentalRequests(Set<RentalRequest> rentalRequests) { this.rentalRequests = rentalRequests; }

    public Set<ViewingRequest> getViewingRequests() { return viewingRequests; }
    public void setViewingRequests(Set<ViewingRequest> viewingRequests) { this.viewingRequests = viewingRequests; }

    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", price=" + price +
                '}';
    }
}
