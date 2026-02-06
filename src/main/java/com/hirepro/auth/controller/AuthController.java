package com.hirepro.auth.controller;

import com.hirepro.auth.dto.ApiResponse;
import com.hirepro.auth.dto.AuthResponse;
import com.hirepro.auth.dto.LoginRequest;
import com.hirepro.auth.dto.RefreshTokenRequest;
import com.hirepro.auth.dto.RegisterRequest;
import com.hirepro.auth.service.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log =
            LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    // -------- Constructor --------

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // -------- Register --------

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(
            @Valid @RequestBody RegisterRequest request) {

        try {
            AuthResponse authResponse = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success(
                            "Registration successful",
                            authResponse
                    ));
        } catch (Exception e) {
            log.error("Registration error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // -------- Login --------

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(
            @Valid @RequestBody LoginRequest request) {

        try {
            AuthResponse authResponse = authService.login(request);
            return ResponseEntity.ok(
                    ApiResponse.success(
                            "Login successful",
                            authResponse
                    )
            );
        } catch (Exception e) {
            log.error("Login error", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // -------- Refresh Token --------

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(
            @Valid @RequestBody RefreshTokenRequest request) {

        try {
            AuthResponse authResponse = authService.refreshToken(request);
            return ResponseEntity.ok(
                    ApiResponse.success(
                            "Token refreshed successfully",
                            authResponse
                    )
            );
        } catch (Exception e) {
            log.error("Token refresh error", e);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // -------- Logout --------

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Void>> logout() {

        try {
            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();

            String username = authentication.getName();
            authService.logout(username);

            return ResponseEntity.ok(
                    ApiResponse.success("Logout successful", null)
            );
        } catch (Exception e) {
            log.error("Logout error", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    // -------- Current User --------

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<String>> getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username = authentication.getName();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Current user retrieved",
                        username
                )
        );
    }
}
