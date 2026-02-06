package com.hirepro.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class RefreshTokenRequest {

    @NotBlank(message = "Refresh token is required")
    private String refreshToken;

    // -------- Constructors --------

    public RefreshTokenRequest() {
    }

    public RefreshTokenRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // -------- Getters & Setters --------

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    // -------- toString (safe) --------
    // Do NOT log refresh tokens
    @Override
    public String toString() {
        return "RefreshTokenRequest{}";
    }
}
