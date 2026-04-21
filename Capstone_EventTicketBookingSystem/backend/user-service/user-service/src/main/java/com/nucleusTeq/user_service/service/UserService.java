package com.nucleusTeq.user_service.service;

import com.nucleusTeq.user_service.dto.AuthResponse;
import com.nucleusTeq.user_service.dto.LoginRequest;
import com.nucleusTeq.user_service.dto.RegisterRequest;

public interface UserService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
