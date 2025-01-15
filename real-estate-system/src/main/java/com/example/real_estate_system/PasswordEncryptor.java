package com.example.real_estate_system;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncryptor {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // Κωδικοί που θέλετε να κρυπτογραφήσετε
        String adminPassword = "admin123";
        String ownerPassword = "owner123";
        String tenantPassword = "tenant123";

        System.out.println("admin123: " + encoder.encode(adminPassword));
        System.out.println("owner123: " + encoder.encode(ownerPassword));
        System.out.println("tenant123: " + encoder.encode(tenantPassword));
    }
}
