package com.nucleusTeq.event_service.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.security.Keys;

public class JwtValidationServiceTest {

    private JwtValidationService jwtValidationService;

    // A sample 256-bit secret key for testing (at least 32 characters)
    private final String testSecret = "my-32-character-ultra-secure-and-ultra-long-secret";

    @BeforeEach
    void setUp() {
        jwtValidationService = new JwtValidationService();
        // Inject the @Value property manually using Spring's ReflectionTestUtils
        ReflectionTestUtils.setField(jwtValidationService, "jwtSecret", testSecret);
    }

    // 1. Valid token validation (Success)
    @Test
    void testExtractEmail_ValidToken_ReturnsEmail() {
        // Arrange: Generate a valid token manually using the same secret
        SecretKey key = Keys.hmacShaKeyFor(testSecret.getBytes(StandardCharsets.UTF_8));
        String validToken = Jwts.builder()
                .subject("john@example.com")
                .claim("role", "CUSTOMER")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour expiration
                .signWith(key)
                .compact();

        // Act
        String extractedEmail = jwtValidationService.extractEmail(validToken);

        // Assert
        assertEquals("john@example.com", extractedEmail);
    }

    // 2. Invalid token validation (Failure)
    @Test
    void testExtractClaims_InvalidToken_ThrowsException() {
        // Arrange
        String invalidToken = "this.is.not.a.valid.jwt";

        // Act & Assert
        assertThrows(JwtException.class, () -> {
            jwtValidationService.extractClaims(invalidToken);
        }, "Expected JwtException for a completely malformed token");
    }

    // 3. Expired token validation (Failure)
    @Test
    void testExtractClaims_ExpiredToken_ThrowsException() {
        // Arrange: Generate a token that expired 1 hour ago
        SecretKey key = Keys.hmacShaKeyFor(testSecret.getBytes(StandardCharsets.UTF_8));
        String expiredToken = Jwts.builder()
                .subject("john@example.com")
                .issuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60 * 2)) // 2 hours ago
                .expiration(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // 1 hour ago
                .signWith(key)
                .compact();

        // Act & Assert
        assertThrows(JwtException.class, () -> {
            jwtValidationService.extractClaims(expiredToken);
        }, "Expected JwtException for an expired token");
    }
}
