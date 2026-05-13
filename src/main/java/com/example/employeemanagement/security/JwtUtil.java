package com.example.employeemanagement.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

// JwtUtil handles all JWT operations:
// 1. Generate token after login
// 2. Validate token on every request
// 3. Extract email/role from token
@Component
public class JwtUtil {

    // Secret key used to sign the token — keep this private!
    private static final String SECRET = "employeeManagementSecretKey2024employeeManagementSecretKey2024";

    // Token valid for 24 hours
    private static final long EXPIRATION = 86400000;

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    // Generate JWT token after successful login
    public String generateToken(String email, String role) {
        return Jwts.builder()
            .setSubject(email)              // store email in token
            .claim("role", role)            // store role in token
            .setIssuedAt(new Date())        // when token was created
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION)) // expiry
            .signWith(getSigningKey())      // sign with secret key
            .compact();
    }

    // Extract email from token
    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    // Extract role from token
    public String extractRole(String token) {
        return getClaims(token).get("role", String.class);
    }

    // Check if token is still valid
    public boolean isTokenValid(String token) {
        try {
            getClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // Parse token and get all claims (data inside token)
    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }
}