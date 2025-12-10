package com.openstage.ticketbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    
    // 1. The Hashing Tool (Bean)
    // We register this so we can inject it into our Service later.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // 2. The Security Rules
    // We disable the default login screen so your HTML frontend works.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable protection for development
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Allow ALL requests (public access)
                );
        return http.build();
    }
}
