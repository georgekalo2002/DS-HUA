package com.example.real_estate_system.controller;

import com.example.real_estate_system.entity.UserRole;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @GetMapping
    public List<UserRole> getAllRoles() {
        return Arrays.asList(UserRole.values()); // ✅ Επιστροφή των ρόλων από το Enum
    }
}
