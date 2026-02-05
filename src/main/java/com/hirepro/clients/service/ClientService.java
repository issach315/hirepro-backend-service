package com.hirepro.clients.service;

import com.hirepro.clients.entity.Client;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClientService {

    /**
     * Save a new client
     * @param client Client to save
     * @return Saved client
     */
    Client saveClient(Client client);

    /**
     * Get all clients
     * @return List of all clients
     */
    List<Client> getAllClients();

    /**
     * Get client by ID
     * @param id Client ID
     * @return Optional containing client if found
     */
    Optional<Client> getClientById(Long id);

    /**
     * Update existing client
     * @param id Client ID
     * @param client Updated client data
     * @return Updated client
     */
    Client updateClient(Long id, Client client);

    /**
     * Partially update client
     * @param id Client ID
     * @param updates Map of fields to update
     * @return Updated client
     */
    Client patchClient(Long id, Map<String, Object> updates);

    /**
     * Delete client by ID
     * @param id Client ID
     */
    void deleteClient(Long id);

    /**
     * Search clients by name (case-insensitive partial match)
     * @param name Name to search for
     * @return List of matching clients
     */
    List<Client> searchClientsByName(String name);

    /**
     * Get total count of clients
     * @return Total number of clients
     */
    long getClientCount();
}