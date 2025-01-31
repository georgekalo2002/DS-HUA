package com.example.real_estate_system;

import com.example.real_estate_system.entity.Role;
import com.example.real_estate_system.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;


@Component
public class DataInitializer {

    private final RoleRepository roleRepository;
    private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

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
        if (!roleRepository.existsByName(roleName)) {
            Role role = new Role();
            role.setName(roleName);
            roleRepository.save(role);
            logger.info("Role created: {}", roleName);
        } else {
            logger.info("Role already exists: {}", roleName);
        }
    }
}
