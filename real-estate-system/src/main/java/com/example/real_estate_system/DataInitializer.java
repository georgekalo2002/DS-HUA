package com.example.real_estate_system;

import com.example.real_estate_system.entity.Role;
import com.example.real_estate_system.repository.RoleRepository;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Optional;

@Component
public class DataInitializer {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        createRoleIfNotFound("ADMIN");
        createRoleIfNotFound("OWNER");
        createRoleIfNotFound("TENANT");
    }

    private void createRoleIfNotFound(String roleName) {
        Optional<Role> roleOptional = roleRepository.findByName(roleName);
        if (roleOptional.isEmpty()) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
            System.out.println("Role created: " + roleName);
        } else {
            System.out.println("Role already exists: " + roleName);
        }
    }
}
