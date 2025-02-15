package com.example.real_estate_system.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Απενεργοποίηση CSRF για δοκιμές
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/register", "/api/public/**").permitAll() // Δημόσιες σελίδες
                .requestMatchers("/admin/**").hasAuthority("ADMIN") // Admin pages
                .requestMatchers("/owner/**").hasAuthority("OWNER") // Owner pages
                .requestMatchers("/tenant/**").hasAuthority("TENANT") // Tenant pages
                .anyRequest().authenticated() // Όλα τα άλλα endpoints απαιτούν authentication
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // ✅ Δημιουργεί session αν χρειάζεται
            )
            .formLogin(form -> form
                .loginPage("/login") // Σελίδα login
                .defaultSuccessUrl("/dashboard", true) // ✅ Μεταφορά στο dashboard μετά το login
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout")
                .permitAll()
            )
            .rememberMe(rememberMe -> rememberMe
                .key("uniqueAndSecret") //  Προσθέτει cookie-based authentication
                .tokenValiditySeconds(86400) // 24 ώρες
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
