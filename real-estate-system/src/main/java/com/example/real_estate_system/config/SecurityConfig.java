package com.example.real_estate_system.config;

import com.example.real_estate_system.CustomLoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Μέσα στο SecurityConfig

@Configuration
public class SecurityConfig {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    private final CustomLoginSuccessHandler successHandler;

    public SecurityConfig(CustomLoginSuccessHandler successHandler) {
        this.successHandler = successHandler;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Αν δε χρειάζεται CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/swagger-ui/**", "/v3/api-docs/**", "/public/**").permitAll() // Επιτρεπτή πρόσβαση στις δημόσιες σελίδες
                .requestMatchers("/admin/**").permitAll()// .hasRole("ADMIN") // Πρόσβαση μόνο για ADMIN
                .requestMatchers("/owner/**").permitAll()//.hasRole("OWNER") // Πρόσβαση μόνο για OWNER
                .requestMatchers("/tenant/**").permitAll()//.hasRole("TENANT") // Πρόσβαση μόνο για TENANT
                .anyRequest().authenticated() // Όλα τα άλλα endpoints απαιτούν αυθεντικοποίηση
            )
            .formLogin(login -> login
                    .successHandler((request, response, authentication) -> {
                        logger.info("User '{}' logged in with roles: {}",
                                authentication.getName(), authentication.getAuthorities());
                        response.sendRedirect("/owner/dashboard");
                    })
                .permitAll()

            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/") // Μετά το logout επιστροφή στην αρχική σελίδα
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
