package com.wegroceries.wegroceriesapi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.wegroceries.wegroceriesapi.users.CustomUserDetailsService;

@Configuration
public class ApiSecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtTokenFilter jwtTokenFilter;

    // Constructor-based injection
    public ApiSecurityConfig(CustomUserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider, JwtTokenFilter jwtTokenFilter) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtTokenFilter = jwtTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Disable CSRF
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/public/**", "/api/auth/**").permitAll() // Allow public and auth endpoints
                .anyRequest().authenticated() // Require authentication for all other requests
            )
            .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class) // Add JWT filter
            .httpBasic(Customizer.withDefaults()); // Use HTTP Basic authentication
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/api/auth/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Password encoder bean
    }
}

