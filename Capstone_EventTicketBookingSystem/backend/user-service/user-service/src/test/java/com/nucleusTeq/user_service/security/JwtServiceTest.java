package com.nucleusTeq.user_service.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.nucleusTeq.user_service.entity.User;

public class JwtServiceTest {

    private JwtService jwtService;
    private User testUser;
    private String secret = "3b63ed1005f186f85bb1e015a114bf8a993978784da5867154a9c52d49ea099e"; // From properties
    private long expiration = 1800000; // 30 minutes

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "jwtSecret", secret);
        ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", expiration);

        testUser = new User();
        testUser.setEmail("test@test.com");
        testUser.setRole("CUSTOMER");
    }

    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken(testUser);
        assertNotNull(token);
        assertTrue(token.length() > 0);
    }

    @Test
    void testExtractEmail() {
        String token = jwtService.generateToken(testUser);
        String extractedEmail = jwtService.extractEmail(token);
        assertEquals("test@test.com", extractedEmail);
    }

    @Test
    void testExtractRole() {
        String token = jwtService.generateToken(testUser);
        String extractedRole = jwtService.extractRole(token);
        assertEquals("CUSTOMER", extractedRole);
    }

    @Test
    void testIsTokenValid() {
        String token = jwtService.generateToken(testUser);
        assertTrue(jwtService.isTokenValid(token, "test@test.com"));
        assertFalse(jwtService.isTokenValid(token, "wrong@test.com"));
    }

    @Test
    void testIsTokenExpired() {
        // Create a token that expires immediately
        ReflectionTestUtils.setField(jwtService, "jwtExpirationMs", -1000L);
        String token = jwtService.generateToken(testUser);

        // Validation should fail due to expiration
        boolean isValid = false;
        try {
            isValid = jwtService.isTokenValid(token, "test@test.com");
        } catch (Exception e) {
            // Depending on JJWT version, it might throw ExpiredJwtException
            // Which means the token is invalid
        }
        assertFalse(isValid);
    }
}
