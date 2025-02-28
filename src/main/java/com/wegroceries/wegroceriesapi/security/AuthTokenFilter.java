package com.wegroceries.wegroceriesapi.security;

import java.io.IOException;
import java.security.Key;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.wegroceries.wegroceriesapi.users.CustomUserDetailsService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class AuthTokenFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Value("${app.jwtSecret}") // Inject the JWT secret from application.properties
    private String jwtSecret;

    private static final Logger logger = LoggerFactory.getLogger(AuthTokenFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);

            if (jwt != null && validateJwt(jwt)) {
                String username = getUsernameFromJwtToken(jwt);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            logger.error("Cannot set user authentication: {}", e);
        }

        filterChain.doFilter(request, response);
    }

    // Method to parse the JWT token from the request
    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7); // Extract the token after "Bearer "
        }

        return null; // No token found
    }

    // Method to validate the JWT token
    private boolean validateJwt(String jwt) {
        try {
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes()); // Create a key from the secret
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt); // Validate the token
            return true;
        } catch (Exception e) {
            return false; // Token is invalid
        }
    }

    // Method to extract the username from the JWT token
    private String getUsernameFromJwtToken(String jwt) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject(); // Extract the username (subject) from the token
    }
}