package com.wegroceries.wegroceriesapi.security;

import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.Set;
import java.util.Map;

@Component
public class JwtTokenProvider {

    private static final String JWT_SECRET = "yourSecretKey"; // Secret key for signing the JWT
    private static final long JWT_EXPIRATION = 604800000L; // 7 days expiration (in milliseconds)

    // Generate a JWT token
    public String generateToken(String username, Set<String> roles) {
        return Jwts.builder()
                .setSubject(username)  // Set the subject (username)
                .claim("roles", roles) // Add roles as a claim
                .setIssuedAt(new Date()) // Set the issue date
                .setExpiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION)) // Set expiration date
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET) // Sign the token
                .compact();
    }

    // Validate the JWT token
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token); // Parse and validate the token
            return true;
        } catch (ExpiredJwtException ex) {
            // Token is expired
            System.out.println("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            // Unsupported JWT token
            System.out.println("Unsupported JWT token");
        } catch (MalformedJwtException ex) {
            // Malformed JWT token
            System.out.println("Malformed JWT token");
        } catch (SignatureException ex) {
            // Invalid signature
            System.out.println("Invalid JWT signature");
        } catch (Exception ex) {
            // Other exceptions
            System.out.println("JWT token validation error");
        }
        return false;
    }

    // Extract username from JWT token
    public String extractUsername(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Return the username
    }

    // Extract roles from JWT token
    public Set<String> extractRoles(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();
        return claims.get("roles", Set.class); // Return the roles
    }

    public String resolveToken(HttpServletRequest request) {
        throw new UnsupportedOperationException("Unimplemented method 'resolveToken'");
    }

    public Authentication getAuthentication(String token) {
        throw new UnsupportedOperationException("Unimplemented method 'getAuthentication'");
    }
}
