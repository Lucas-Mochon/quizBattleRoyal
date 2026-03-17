package com.quizbattleroyale.controller;

import org.springframework.http.ResponseEntity;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.*;

import com.quizbattleroyale.dto.auth.request.CreateUserDto;
import com.quizbattleroyale.dto.auth.request.LoginDto;
import com.quizbattleroyale.dto.auth.response.LoginResponse;
import com.quizbattleroyale.service.AuthService;
import com.quizbattleroyale.utils.ApiResponse;
import com.quizbattleroyale.utils.JwtUtils;

@RestController
public class AuthController {
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    // POST /auth/register
    @PostMapping("/register")
    public ResponseEntity<Object> register(
            @RequestBody CreateUserDto createUserDTO,
            HttpServletResponse response) {
        try {
            LoginResponse loginResponse = authService.register(createUserDTO, response);
            return ResponseEntity.ok(ApiResponse.success(loginResponse));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // POST /auth/login
    @PostMapping("/login")
    public ResponseEntity<Object> login(
            @RequestBody LoginDto loginDto,
            HttpServletResponse response) {
        try {
            LoginResponse loginResponse = authService.login(loginDto, response);
            return ResponseEntity.ok(ApiResponse.success(loginResponse));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(e.getMessage()));
        }
    }

    // POST /auth/refresh
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<String>> refresh(
            HttpServletRequest request,
            HttpServletResponse response) {

        try {
            String refreshToken = jwtUtils.extractRefreshTokenFromCookie(request);

            if (refreshToken == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Missing refresh token"));
            }

            String newAccessToken = authService.refresh(refreshToken, response);

            return ResponseEntity.ok(ApiResponse.success(newAccessToken));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // POST /auth/logout
    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logout(
            HttpServletRequest request,
            HttpServletResponse response) {
        try {
            String refreshToken = jwtUtils.extractRefreshTokenFromCookie(request);

            if (refreshToken == null) {
                return ResponseEntity.badRequest()
                        .body(ApiResponse.error("Missing refresh token"));
            }

            authService.logout(refreshToken, response);
            return ResponseEntity.ok(ApiResponse.success("Successfully logged out"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(ApiResponse.error(e.getMessage()));
        }
    }
}
