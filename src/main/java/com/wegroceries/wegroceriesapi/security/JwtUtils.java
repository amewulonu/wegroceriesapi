package com.wegroceries.wegroceriesapi.security;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${wegroceries.app.jwtSecret}")
    private String jwtSecret;

    @Value("${wegroceries.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        // Cast the principal to UserDetailsImpl (ensure this class exists in your project)
        UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

        // Generate JWT token
        return Jwts.builder()
                .setSubject(userPrincipal.getUsername()) // Set subject to username
                .setIssuedAt(new Date()) // Set the issue date
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Set expiration time
                .signWith(key(), SignatureAlgorithm.HS256) // Sign with HMAC using JWT secret
                .compact(); // Compact the JWT into a string
    }

    private Key key() {
        // Decode the base64 secret and generate the key
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getUserNameFromJwtToken(String token) {
        try {
            // Parse the token and get the username (subject)
            return Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (JwtException e) {
            logger.error("JWT parsing error: {}", e.getMessage());
        }
        return null;
    }

    public boolean validateJwtToken(String authToken) {
        try {
            // Validate the JWT token
            Jwts.parserBuilder()
                    .setSigningKey(key())
                    .build()
                    .parse(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }
}