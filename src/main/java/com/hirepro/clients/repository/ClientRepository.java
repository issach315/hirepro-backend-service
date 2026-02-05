package com.hirepro.clients.repository;

import com.hirepro.clients.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Find clients by name containing the given string (case-insensitive)
    List<Client> findByClientNameContainingIgnoreCase(String name);

    // Find client by exact email
    Optional<Client> findByClientEmail(String email);

    // Find clients by industry
    List<Client> findByIndustry(String industry);

    // Find clients by location
    List<Client> findByClientLocation(String location);

    // Custom query to search clients by multiple fields
    @Query("SELECT c FROM Client c WHERE " +
            "LOWER(c.clientName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.clientEmail) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.industry) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.clientLocation) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Client> searchClients(@Param("searchTerm") String searchTerm);

    // Check if client exists by email
    boolean existsByClientEmail(String email);

    // Count clients by industry
    long countByIndustry(String industry);
}