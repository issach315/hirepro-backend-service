package com.hirepro.auth.dto;

import com.hirepro.auth.entity.Role;

public class AuthResponse {

    private String userId;
    private String fullName;
    private String username;
    private Role role;
    private String accessToken;
    private Long accessTokenExpiresAt;
    private String refreshToken;
    private Long refreshTokenExpiresAt;
    private String tokenType;

    // -------- Constructors --------

    public AuthResponse() {
    }

    public AuthResponse(String userId, String fullName, String username,
                        Role role, String accessToken, Long accessTokenExpiresAt,
                        String refreshToken, Long refreshTokenExpiresAt,
                        String tokenType) {
        this.userId = userId;
        this.fullName = fullName;
        this.username = username;
        this.role = role;
        this.accessToken = accessToken;
        this.accessTokenExpiresAt = accessTokenExpiresAt;
        this.refreshToken = refreshToken;
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
        this.tokenType = tokenType;
    }

    // -------- Getters & Setters --------

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getAccessTokenExpiresAt() {
        return accessTokenExpiresAt;
    }

    public void setAccessTokenExpiresAt(Long accessTokenExpiresAt) {
        this.accessTokenExpiresAt = accessTokenExpiresAt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getRefreshTokenExpiresAt() {
        return refreshTokenExpiresAt;
    }

    public void setRefreshTokenExpiresAt(Long refreshTokenExpiresAt) {
        this.refreshTokenExpiresAt = refreshTokenExpiresAt;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    // -------- toString (safe) --------
    // Tokens should generally NOT be logged in production
    @Override
    public String toString() {
        return "AuthResponse{" +
                "userId='" + userId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", username='" + username + '\'' +
                ", role=" + role +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}
