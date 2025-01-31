package com.example.real_estate_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication
public class RealEstateSystemApplication {

    private static final Logger logger = LoggerFactory.getLogger(RealEstateSystemApplication.class);

    public static void main(String[] args) {
        logger.info("Starting the Real Estate System Application...");
        SpringApplication.run(RealEstateSystemApplication.class, args);
    }
}
