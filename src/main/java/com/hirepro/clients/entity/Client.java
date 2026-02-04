package com.hirepro.clients.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;
    private String clientEmail;
    private String clientLocation;
    private String clientPhone;
    private String industry;
    private String contactPerson;

    // Default constructor
    public Client() {
    }

    // All-args constructor
    public Client(String clientName, String clientEmail, String clientLocation, String clientPhone, String industry, String contactPerson) {
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
                '}';
    }
}
