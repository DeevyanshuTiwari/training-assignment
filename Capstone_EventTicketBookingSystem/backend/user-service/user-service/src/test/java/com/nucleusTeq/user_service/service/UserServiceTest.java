package com.nucleusTeq.user_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nucleusTeq.user_service.dto.AuthResponse;
import com.nucleusTeq.user_service.dto.LoginRequest;
import com.nucleusTeq.user_service.dto.RegisterRequest;
import com.nucleusTeq.user_service.entity.User;
import com.nucleusTeq.user_service.exception.BadRequestException;
import com.nucleusTeq.user_service.repository.UserRepository;
import com.nucleusTeq.user_service.security.JwtService;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private UserServiceImpl userService;

    private RegisterRequest validRegisterRequest;
    private LoginRequest validLoginRequest;
    private User mockUser;

    @BeforeEach
    void setUp() {
        // Arrange: Setup standard test inputs
        validRegisterRequest = new RegisterRequest();
        validRegisterRequest.setFullName("John Doe");
        validRegisterRequest.setEmail("john@gmail.com");
        validRegisterRequest.setPassword("Password@123");
        validRegisterRequest.setPhone("1234567890");
        validRegisterRequest.setRole("CUSTOMER");

        validLoginRequest = new LoginRequest();
        validLoginRequest.setEmail("john@gmail.com");
        validLoginRequest.setPassword("Password@123");

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setFullName("John Doe");
        mockUser.setEmail("john@gmail.com");
        mockUser.setPassword("encodedPassword123");
        mockUser.setPhone("1234567890");
        mockUser.setRole("CUSTOMER");
    }

    // 1. Successful Registration
    @Test
    void testRegister_Success() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByPhone(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword123");
        when(userRepository.save(any(User.class))).thenReturn(mockUser);

        // Act
        AuthResponse response = userService.register(validRegisterRequest);

        // Assert
        assertNotNull(response);
        assertEquals("User registered successfully.", response.getMessage());
        assertEquals("john@gmail.com", response.getEmail());
        verify(userRepository).save(any(User.class)); // Verifies the repository save was called
    }

    // 2. Duplicate Email Registration (Failure)
    @Test
    void testRegister_DuplicateEmail_ThrowsException() {
        // Arrange
        when(userRepository.existsByEmail("john@gmail.com")).thenReturn(true);

        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.register(validRegisterRequest);
        });

        assertEquals("Email is already registered.", exception.getMessage());
    }

    // 3. Successful Login
    @Test
    void testLogin_Success() {
        // Arrange
        when(userRepository.findByEmail("john@gmail.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("Password@123", "encodedPassword123")).thenReturn(true);
        when(jwtService.generateToken(mockUser)).thenReturn("mock.jwt.token");
        when(userRepository.save(mockUser)).thenReturn(mockUser); // Due to lastActivityAt update

        // Act
        AuthResponse response = userService.login(validLoginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("Login successful.", response.getMessage());
        assertEquals("mock.jwt.token", response.getToken());
        verify(jwtService).generateToken(mockUser);
    }

    // 4. Invalid Login (Wrong Password)
    @Test
    void testLogin_InvalidPassword_ThrowsException() {
        // Arrange
        when(userRepository.findByEmail("john@gmail.com")).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("Password@123", "encodedPassword123")).thenReturn(false);

        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.login(validLoginRequest);
        });

        assertEquals("Invalid email or password.", exception.getMessage());
    }

    // 5. Validation failures
    @Test
    void testRegister_ValidationFailures_ThrowsException() {
        // Missing Request
        assertThrows(BadRequestException.class, () -> userService.register(null));

        // Invalid Name
        validRegisterRequest.setFullName("A");
        assertThrows(BadRequestException.class, () -> userService.register(validRegisterRequest));

        // Invalid Email
        validRegisterRequest.setFullName("John Doe");
        validRegisterRequest.setEmail("invalid-email");
        assertThrows(BadRequestException.class, () -> userService.register(validRegisterRequest));

        // Invalid Password
        validRegisterRequest.setEmail("john@gmail.com");
        validRegisterRequest.setPassword("weak");
        assertThrows(BadRequestException.class, () -> userService.register(validRegisterRequest));

        // Invalid Phone
        validRegisterRequest.setPassword("Password@123");
        validRegisterRequest.setPhone("123");
        assertThrows(BadRequestException.class, () -> userService.register(validRegisterRequest));

        // Invalid Role
        validRegisterRequest.setPhone("1234567890");
        validRegisterRequest.setRole("INVALID_ROLE");
        assertThrows(BadRequestException.class, () -> userService.register(validRegisterRequest));
    }
    // 6. Duplicate Phone Registration (Failure)
    @Test
    void testRegister_DuplicatePhone_ThrowsException() {
        // Arrange
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByPhone("1234567890")).thenReturn(true);

        // Act & Assert
        BadRequestException exception = assertThrows(BadRequestException.class, () -> {
            userService.register(validRegisterRequest);
        });

        assertEquals("Phone number is already registered.", exception.getMessage());
    }

    // 7. Login Validation Failures
    @Test
    void testLogin_ValidationFailures_ThrowsException() {
        // Missing Request
        assertThrows(BadRequestException.class, () -> userService.login(null));

        // Missing Email
        validLoginRequest.setEmail("");
        assertThrows(BadRequestException.class, () -> userService.login(validLoginRequest));

        // Missing Password
        validLoginRequest.setEmail("john@gmail.com");
        validLoginRequest.setPassword(null);
        assertThrows(BadRequestException.class, () -> userService.login(validLoginRequest));
    }
}
