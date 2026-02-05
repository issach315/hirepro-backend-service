package com.hirepro.clients.service;

import com.hirepro.clients.entity.Client;
import com.hirepro.clients.repository.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client saveClient(Client client) {
        logger.debug("Saving client: {}", client);
        return clientRepository.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> getAllClients() {
        logger.debug("Fetching all clients");
        return clientRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Client> getClientById(Long id) {
        logger.debug("Fetching client by ID: {}", id);
        return clientRepository.findById(id);
    }

    @Override
    public Client updateClient(Long id, Client client) {
        logger.debug("Updating client with ID: {}", id);
        return clientRepository.findById(id).map(existingClient -> {
            existingClient.setClientName(client.getClientName());
            existingClient.setClientEmail(client.getClientEmail());
            existingClient.setClientLocation(client.getClientLocation());
            existingClient.setClientPhone(client.getClientPhone());
            existingClient.setIndustry(client.getIndustry());
            existingClient.setContactPerson(client.getContactPerson());
            return clientRepository.save(existingClient);
        }).orElseThrow(() -> {
            logger.error("Client not found with id: {}", id);
            return new RuntimeException("Client not found with id " + id);
        });
    }

    @Override
    public Client patchClient(Long id, Map<String, Object> updates) {
        logger.debug("Patching client with ID: {} with updates: {}", id, updates);
        return clientRepository.findById(id).map(existingClient -> {
            updates.forEach((key, value) -> {
                switch (key) {
                    case "clientName":
                        existingClient.setClientName((String) value);
                        break;
                    case "clientEmail":
                        existingClient.setClientEmail((String) value);
                        break;
                    case "clientLocation":
                        existingClient.setClientLocation((String) value);
                        break;
                    case "clientPhone":
                        existingClient.setClientPhone((String) value);
                        break;
                    case "industry":
                        existingClient.setIndustry((String) value);
                        break;
                    case "contactPerson":
                        existingClient.setContactPerson((String) value);
                        break;
                    default:
                        logger.warn("Ignoring unknown field: {}", key);
                }
            });
            return clientRepository.save(existingClient);
        }).orElseThrow(() -> {
            logger.error("Client not found with id: {}", id);
            return new RuntimeException("Client not found with id " + id);
        });
    }

    @Override
    public void deleteClient(Long id) {
        logger.debug("Deleting client with ID: {}", id);
        if (!clientRepository.existsById(id)) {
            logger.error("Client not found with id: {}", id);
            throw new RuntimeException("Client not found with id " + id);
        }
        clientRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Client> searchClientsByName(String name) {
        logger.debug("Searching clients by name: {}", name);
        return clientRepository.findByClientNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public long getClientCount() {
        logger.debug("Counting total clients");
        return clientRepository.count();
    }
}