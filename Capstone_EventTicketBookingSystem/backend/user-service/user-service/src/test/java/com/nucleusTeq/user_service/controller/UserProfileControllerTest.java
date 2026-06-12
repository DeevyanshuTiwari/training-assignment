package com.nucleusTeq.user_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nucleusTeq.user_service.controller.UserProfileController.UserProfileDto;
import com.nucleusTeq.user_service.entity.User;
import com.nucleusTeq.user_service.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
public class UserProfileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserProfileController userProfileController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private User user;
    private UserProfileDto updateRequest;
    private Authentication authentication;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userProfileController).build();

        user = new User();
        user.setId(1L);
        user.setFullName("Test User");
        user.setEmail("user@test.com");
        user.setPhone("1234567890");

        updateRequest = new UserProfileDto();
        updateRequest.name = "Updated User";
        updateRequest.email = "updated@test.com";
        updateRequest.phone = "0987654321";

        authentication = new UsernamePasswordAuthenticationToken("user@test.com", null, Collections.emptyList());
    }

    @Test
    void testGetProfileSuccess() throws Exception {
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/me").principal(authentication))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test User"))
                .andExpect(jsonPath("$.email").value("user@test.com"))
                .andExpect(jsonPath("$.phone").value("1234567890"));
    }

    @Test
    void testGetProfileNotFound() throws Exception {
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/me").principal(authentication))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("User not found"));
    }

    @Test
    void testUpdateProfileSuccess() throws Exception {
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("updated@test.com")).thenReturn(false);
        when(userRepository.existsByPhone("0987654321")).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(user);

        mockMvc.perform(put("/api/users/me").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Profile updated successfully"));
    }

    @Test
    void testUpdateProfileEmailExists() throws Exception {
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail("updated@test.com")).thenReturn(true);

        mockMvc.perform(put("/api/users/me").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Email already in use"));
    }

    @Test
    void testUpdateProfilePhoneExists() throws Exception {
        updateRequest.email = "user@test.com"; // Keep email same to bypass email check
        when(userRepository.findByEmail("user@test.com")).thenReturn(Optional.of(user));
        when(userRepository.existsByPhone("0987654321")).thenReturn(true);

        mockMvc.perform(put("/api/users/me").principal(authentication)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Phone number already in use"));
    }
}
