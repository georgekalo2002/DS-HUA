package com.example.real_estate_system;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // Παίρνουμε τους ρόλους του χρήστη
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        String targetUrl = "/";

        if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            targetUrl = "/admin/dashboard";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_OWNER"))) {
            targetUrl = "/owner/dashboard";
        } else if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_TENANT"))) {
            targetUrl = "/tenant/dashboard";
        }

        response.sendRedirect(targetUrl);
    }
}
