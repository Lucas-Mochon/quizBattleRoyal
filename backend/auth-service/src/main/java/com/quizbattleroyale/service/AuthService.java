package com.quizbattleroyale.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.quizbattleroyale.dto.auth.request.CreateUserDto;
import com.quizbattleroyale.dto.auth.request.LoginDto;
import com.quizbattleroyale.dto.auth.response.LoginResponse;
import com.quizbattleroyale.entity.User;
import com.quizbattleroyale.enums.UserRoleEnum;
import com.quizbattleroyale.repository.UserRepository;
import com.quizbattleroyale.repository.UserRoleRepository;
import com.quizbattleroyale.utils.ByteaConvertor;
import com.quizbattleroyale.utils.CookieUtils;
import com.quizbattleroyale.utils.JwtUtils;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final CookieUtils cookieUtils;
    private final ByteaConvertor byteaConvertor;
    private final UserRoleRepository userRoleRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils, CookieUtils cookieUtils, ByteaConvertor byteaConvertor, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
        this.cookieUtils = cookieUtils;
        this.byteaConvertor = byteaConvertor;
        this.userRoleRepository = userRoleRepository;
    }

    @Transactional
    public LoginResponse register(CreateUserDto createUserDTO, HttpServletResponse response) {
        Optional<User> existingUser = userRepository.findByEmail(createUserDTO.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        User user = new User();
        user.setEmail(createUserDTO.getEmail());
        user.setUsername(createUserDTO.getUsername());
        user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
        user.setPicture(byteaConvertor.getDefaultPicture());
        user.setRole(userRoleRepository.findByName(UserRoleEnum.USER.name()));
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        String refreshToken = jwtUtils.generateRefreshToken(savedUser);
        String accessToken = jwtUtils.generateJwtToken(savedUser);

        userRepository.setRefreshToken(savedUser.getId(), refreshToken);

        cookieUtils.setRefreshTokenCookie(response, refreshToken);
        cookieUtils.setAccessTokenCookie(response, accessToken);

        return new LoginResponse(
                savedUser.getId().toString(),
                savedUser.getEmail(),
                savedUser.getUsername(),
                accessToken);
    }

    @Transactional
    public LoginResponse login(LoginDto loginDto, HttpServletResponse response) {
        Optional<User> user = userRepository.findByEmail(loginDto.getEmail());

        if (user.isEmpty()) {
            throw new RuntimeException("Invalid email or password");
        }

        User existingUser = user.get();

        if (!passwordEncoder.matches(loginDto.getPassword(), existingUser.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String refreshToken = jwtUtils.generateRefreshToken(existingUser);
        String accessToken = jwtUtils.generateJwtToken(existingUser);

        userRepository.setRefreshToken(existingUser.getId(), refreshToken);

        cookieUtils.setRefreshTokenCookie(response, refreshToken);
        cookieUtils.setAccessTokenCookie(response, accessToken);

        return new LoginResponse(
                existingUser.getId().toString(),
                existingUser.getEmail(),
                existingUser.getUsername(),
                accessToken);
    }

    @Transactional
    public String refresh(String refreshToken, HttpServletResponse response) {
        String userId = jwtUtils.validateTokenAndGetUserId(refreshToken);

        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();
        String storedRefreshToken = userRepository.getRefreshToken(user.getId()).orElse(null);

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new RuntimeException("Invalid refresh token");
        }

        if (!jwtUtils.isTokenValid(refreshToken)) {
            throw new RuntimeException("Refresh token expired");
        }

        String newAccessToken = jwtUtils.generateJwtToken(user);
        String newRefreshToken = jwtUtils.generateRefreshToken(user);

        userRepository.setRefreshToken(user.getId(), newRefreshToken);

        cookieUtils.setRefreshTokenCookie(response, newRefreshToken);
        cookieUtils.setAccessTokenCookie(response, newAccessToken);

        return newAccessToken;
    }

    @Transactional
    public void logout(String refreshToken, HttpServletResponse response) {
        String userId = jwtUtils.validateTokenAndGetUserId(refreshToken);

        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        User user = userOpt.get();

        userRepository.setRefreshToken(user.getId(), null);

        cookieUtils.clearCookies(response);
    }
}
