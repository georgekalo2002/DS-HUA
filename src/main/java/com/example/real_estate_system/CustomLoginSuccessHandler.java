package com.example.real_estate_system;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(CustomLoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Παίρνουμε τους ρόλους του χρήστη
        Set<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        String targetUrl = "/dashboard"; // Προκαθορισμένο dashboard

        if (roles.contains("ROLE_ADMIN")) {
            targetUrl = "/admin/dashboard";
        } else if (roles.contains("ROLE_OWNER")) {
            targetUrl = "/owner/dashboard";
        } else if (roles.contains("ROLE_TENANT")) {
            targetUrl = "/tenant/dashboard";
        }

        logger.info("User '{}' authenticated successfully. Redirecting to: {}", authentication.getName(), targetUrl);
        
        response.sendRedirect(targetUrl);
    }
}
