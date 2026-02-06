package com.hirepro.user.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_profile")
public class UserProfile {

    @Id
    @Column(name = "user_id", nullable = false, length = 26)
    private String userId;  // ULID, links to User.userId

    @Column(name = "personal_email", nullable = false, unique = true)
    private String personalEmail;

    @Column(name = "work_email", nullable = false, unique = true)
    private String workEmail;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "gender")
    private String gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "status")
    private String status;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // -------- Constructors --------

    public UserProfile() {}

    public UserProfile(String userId, String personalEmail, String workEmail,
                       String phoneNumber, String gender, LocalDate dateOfBirth, String status) {
        this.userId = userId;
        this.personalEmail = personalEmail;
        this.workEmail = workEmail;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.status = status;
    }

    // -------- Lifecycle Hook for ULID --------
    @PrePersist
    public void prePersist() {
        if (this.userId == null || this.userId.isEmpty()) {
            this.userId = com.github.f4b6a3.ulid.UlidCreator.getUlid().toString();
        }
    }

    // -------- Getters & Setters --------
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getPersonalEmail() { return personalEmail; }
    public void setPersonalEmail(String personalEmail) { this.personalEmail = personalEmail; }

    public String getWorkEmail() { return workEmail; }
    public void setWorkEmail(String workEmail) { this.workEmail = workEmail; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "UserProfile{" +
                "userId='" + userId + '\'' +
                ", personalEmail='" + personalEmail + '\'' +
                ", workEmail='" + workEmail + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", gender='" + gender + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
