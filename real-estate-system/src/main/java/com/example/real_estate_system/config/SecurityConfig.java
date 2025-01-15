package com.example.real_estate_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Αν δε χρειάζεται CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/swagger-ui/**", "/v3/api-docs/**", "/public/**").permitAll() // Επιτρεπτή πρόσβαση στις δημόσιες σελίδες
                .anyRequest().authenticated() // Όλα τα άλλα endpoints απαιτούν αυθεντικοποίηση
            )
            .formLogin(login -> login // Προεπιλεγμένη σελίδα σύνδεσης του Spring Security
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
