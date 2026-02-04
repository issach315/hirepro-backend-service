package com.hirepro.clients.service;

import com.hirepro.clients.entity.Client;
import com.hirepro.clients.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client updateClient(Long id, Client client) {
        return clientRepository.findById(id).map(existingClient -> {
            existingClient.setClientName(client.getClientName());
            existingClient.setClientEmail(client.getClientEmail());
            existingClient.setClientLocation(client.getClientLocation());
            existingClient.setClientPhone(client.getClientPhone());
            existingClient.setIndustry(client.getIndustry());
            existingClient.setContactPerson(client.getContactPerson());
            return clientRepository.save(existingClient);
        }).orElseThrow(() -> new RuntimeException("Client not found with id " + id));
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
