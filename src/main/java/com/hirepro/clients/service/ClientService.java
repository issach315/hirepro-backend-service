package com.hirepro.clients.service;

import com.hirepro.clients.entity.Client;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ClientService {

    Client saveClient(Client client);

    List<Client> getAllClients();

    Optional<Client> getClientById(Long id);

    Client updateClient(Long id, Client client);

    Client patchClient(Long id, Map<String, Object> updates);

    void deleteClient(Long id);

    List<Client> searchClientsByName(String name);

    long getClientCount();
}