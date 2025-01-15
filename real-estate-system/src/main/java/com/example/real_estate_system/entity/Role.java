package com.example.real_estate_system.entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@JsonIdentityInfo(
    generator = ObjectIdGenerators.PropertyGenerator.class,
    property = "id"
)
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name; // Όνομα ρόλου (π.χ., ADMIN, OWNER, TENANT)

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<User> users; // Οι χρήστες που έχουν αυτόν τον ρόλο

    @Override
    public String getAuthority() {
        return name; // Επιστρέφει το όνομα του ρόλου ως GrantedAuthority
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this); // Επιστρέφει τον ίδιο τον ρόλο ως GrantedAuthority
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
