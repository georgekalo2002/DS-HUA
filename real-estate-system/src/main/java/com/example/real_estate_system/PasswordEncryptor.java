package com.example.real_estate_system;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PasswordEncryptor {

    private static final Logger logger = LoggerFactory.getLogger(PasswordEncryptor.class);
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encryptPassword(String password) {
        return encoder.encode(password);
    }

    public static void main(String[] args) {
        String adminPassword = "admin123";
        String ownerPassword = "owner123";
        String tenantPassword = "tenant123";

        String encryptedAdminPassword = encryptPassword(adminPassword);
        String encryptedOwnerPassword = encryptPassword(ownerPassword);
        String encryptedTenantPassword = encryptPassword(tenantPassword);

        logger.info("Encrypted admin password: {}", encryptedAdminPassword);
        logger.info("Encrypted owner password: {}", encryptedOwnerPassword);
        logger.info("Encrypted tenant password: {}", encryptedTenantPassword);
    }
}
