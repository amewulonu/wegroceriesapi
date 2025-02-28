package com.wegroceries.wegroceriesapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.wegroceries.wegroceriesapi.users.CustomUserDetailsService;

@Configuration
@EnableMethodSecurity(
    securedEnabled = true, // Enables @Secured annotation
    jsr250Enabled = true,  // Enables @RolesAllowed annotation
    prePostEnabled = true  // Enables @PreAuthorize and @PostAuthorize annotations
)
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll() // Public endpoints for authentication
                .requestMatchers("/api/test/**").permitAll() // Public endpoints for testing
                .requestMatchers("/api/admin/**").hasRole("ADMIN") // Only accessible to ADMINs
                .requestMatchers("/api/user/**").hasAnyRole("USER", "ADMIN") // Accessible to USER and ADMIN
                .requestMatchers("/api/manager/**").hasRole("MANAGER") // Accessible to MANAGER
                .requestMatchers("/api/support/**").hasRole("SUPPORT") // Accessible to SUPPORT
                .requestMatchers("/api/vendor/**").hasRole("VENDOR") // Accessible to VENDOR
                .requestMatchers("/api/delivery/**").hasRole("DELIVERY_PERSON") // Accessible to DELIVERY_PERSON
                .requestMatchers("/api/analyst/**").hasRole("ANALYST") // Accessible to ANALYST
                .requestMatchers("/api/marketer/**").hasRole("MARKETER") // Accessible to MARKETER
                .requestMatchers("/api/developer/**").hasRole("DEVELOPER") // Accessible to DEVELOPER
                .requestMatchers("/api/customer/**").hasRole("CUSTOMER") // Accessible to CUSTOMER
                .anyRequest().authenticated(); // Any other requests require authentication

        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}