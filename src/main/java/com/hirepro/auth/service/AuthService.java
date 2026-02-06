package com.hirepro.auth.service;

import com.hirepro.auth.dto.AuthResponse;
import com.hirepro.auth.dto.LoginRequest;
import com.hirepro.auth.dto.RefreshTokenRequest;
import com.hirepro.auth.dto.RegisterRequest;
import com.hirepro.auth.entity.RefreshToken;
import com.hirepro.auth.entity.User;
import com.hirepro.auth.repository.RefreshTokenRepository;
import com.hirepro.auth.repository.UserRepository;
import com.hirepro.auth.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class AuthService {

    private static final Logger log =
            LoggerFactory.getLogger(AuthService.class);

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    // -------- Constructor --------

    public AuthService(UserRepository userRepository,
                       RefreshTokenRepository refreshTokenRepository,
                       PasswordEncoder passwordEncoder,
                       JwtUtil jwtUtil,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    // -------- Register --------

    @Transactional
    public AuthResponse register(RegisterRequest request) {

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        user.setIsActive(true);

        User savedUser = userRepository.save(user);
        log.info("New user registered: {}", savedUser.getUsername());

        return generateAuthResponse(savedUser);
    }

    // -------- Login --------

    @Transactional
    public AuthResponse login(LoginRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            if (!Boolean.TRUE.equals(user.getIsActive())) {
                throw new RuntimeException("User account is disabled");
            }

            log.info("User logged in successfully: {}", user.getUsername());

            return generateAuthResponse(user);

        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    // -------- Refresh Token --------

    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest request) {

        RefreshToken refreshToken = refreshTokenRepository
                .findByToken(request.getRefreshToken())
                .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh token has expired");
        }

        if (Boolean.TRUE.equals(refreshToken.getIsRevoked())) {
            throw new RuntimeException("Refresh token has been revoked");
        }

        User user = refreshToken.getUser();

        String newAccessToken =
                jwtUtil.generateAccessToken(
                        user.getUserId(),
                        user.getUsername(),
                        user.getRole()
                );

        String newRefreshToken =
                jwtUtil.generateRefreshToken(
                        user.getUserId(),
                        user.getUsername()
                );

        refreshTokenRepository.delete(refreshToken);

        RefreshToken newTokenEntity = new RefreshToken();
        newTokenEntity.setToken(newRefreshToken);
        newTokenEntity.setUser(user);
        newTokenEntity.setIsRevoked(false);
        newTokenEntity.setExpiresAt(
                LocalDateTime.now().plusSeconds(
                        jwtUtil.getRefreshTokenExpiration() / 1000
                )
        );

        refreshTokenRepository.save(newTokenEntity);

        long now = System.currentTimeMillis();

        return new AuthResponse(
                user.getUserId(),
                user.getFullName(),
                user.getUsername(),
                user.getRole(),
                newAccessToken,
                now + jwtUtil.getAccessTokenExpiration(),
                newRefreshToken,
                now + jwtUtil.getRefreshTokenExpiration(),
                "Bearer"
        );
    }

    // -------- Logout --------

    @Transactional
    public void logout(String username) {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        refreshTokenRepository.deleteByUser(user);
        log.info("User logged out successfully: {}", username);
    }

    // -------- Helper --------

    private AuthResponse generateAuthResponse(User user) {

        String accessToken =
                jwtUtil.generateAccessToken(
                        user.getUserId(),
                        user.getUsername(),
                        user.getRole()
                );

        String refreshToken =
                jwtUtil.generateRefreshToken(
                        user.getUserId(),
                        user.getUsername()
                );

        RefreshToken tokenEntity = new RefreshToken();
        tokenEntity.setToken(refreshToken);
        tokenEntity.setUser(user);
        tokenEntity.setIsRevoked(false);
        tokenEntity.setExpiresAt(
                LocalDateTime.now().plusSeconds(
                        jwtUtil.getRefreshTokenExpiration() / 1000
                )
        );

        refreshTokenRepository.save(tokenEntity);

        long now = System.currentTimeMillis();

        return new AuthResponse(
                user.getUserId(),
                user.getFullName(),
                user.getUsername(),
                user.getRole(),
                accessToken,
                now + jwtUtil.getAccessTokenExpiration(),
                refreshToken,
                now + jwtUtil.getRefreshTokenExpiration(),
                "Bearer"
        );
    }
}
