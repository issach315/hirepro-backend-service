package com.hirepro.clients.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Client name is required")
    @Size(max = 255, message = "Client name must not exceed 255 characters")
    @Column(name = "client_name", nullable = false)
    private String clientName;

    @NotBlank(message = "Client email is required")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @Column(name = "client_email", nullable = false)
    private String clientEmail;

    @Size(max = 255, message = "Location must not exceed 255 characters")
    @Column(name = "client_location")
    private String clientLocation;

    @Size(max = 50, message = "Phone must not exceed 50 characters")
    @Column(name = "client_phone", length = 50)
    private String clientPhone;

    @Size(max = 255, message = "Industry must not exceed 255 characters")
    @Column(name = "industry")
    private String industry;

    @Size(max = 255, message = "Contact person must not exceed 255 characters")
    @Column(name = "contact_person")
    private String contactPerson;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Default constructor
    public Client() {
    }

    // Constructor without ID and timestamps
    public Client(String clientName, String clientEmail, String clientLocation,
                  String clientPhone, String industry, String contactPerson) {
        this.clientName = clientName;
        this.clientEmail = clientEmail;
        this.clientLocation = clientLocation;
        this.clientPhone = clientPhone;
        this.industry = industry;
        this.contactPerson = contactPerson;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientEmail() {
        return clientEmail;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public String getClientLocation() {
        return clientLocation;
    }

    public void setClientLocation(String clientLocation) {
        this.clientLocation = clientLocation;
    }

    public String getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", clientName='" + clientName + '\'' +
                ", clientEmail='" + clientEmail + '\'' +
                ", clientLocation='" + clientLocation + '\'' +
                ", clientPhone='" + clientPhone + '\'' +
                ", industry='" + industry + '\'' +
                ", contactPerson='" + contactPerson + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
