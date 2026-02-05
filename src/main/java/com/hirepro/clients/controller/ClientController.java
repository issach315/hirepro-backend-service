package com.hirepro.clients.controller;

import com.hirepro.clients.entity.Client;
import com.hirepro.clients.service.ClientService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/clients")
@CrossOrigin(origins = "*")
public class ClientController {

    private static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    @Autowired
    private ClientService clientService;

    @Value("${app.environment:unknown}")
    private String environment;

    // ============ HEALTH CHECK ============
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("environment", environment);
        response.put("service", "clients");
        return ResponseEntity.ok(response);
    }

    // ============ CREATE CLIENT ============
    @PostMapping
    public ResponseEntity<Client> createClient(@Valid @RequestBody Client client) {
        logger.info("Creating new client: {}", client.getClientName());
        Client savedClient = clientService.saveClient(client);
        logger.info("Client created successfully with ID: {}", savedClient.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedClient);
    }

    // ============ GET ALL CLIENTS ============
    @GetMapping
    public ResponseEntity<List<Client>> getAllClients() {
        logger.info("Fetching all clients");
        List<Client> clients = clientService.getAllClients();
        logger.info("Found {} clients", clients.size());
        return ResponseEntity.ok(clients);
    }

    // ============ GET CLIENT BY ID ============
    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        logger.info("Fetching client with ID: {}", id);
        return clientService.getClientById(id)
                .map(client -> {
                    logger.info("Client found: {}", client.getClientName());
                    return ResponseEntity.ok(client);
                })
                .orElseGet(() -> {
                    logger.warn("Client not found with ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    // ============ UPDATE CLIENT (FULL) ============
    @PutMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @Valid @RequestBody Client client) {
        logger.info("Updating client with ID: {}", id);
        Client updatedClient = clientService.updateClient(id, client);
        logger.info("Client updated successfully: {}", updatedClient.getClientName());
        return ResponseEntity.ok(updatedClient);
    }

    // ============ PARTIAL UPDATE CLIENT ============
    @PatchMapping("/{id}")
    public ResponseEntity<Client> patchClient(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        logger.info("Patching client with ID: {}", id);
        Client patchedClient = clientService.patchClient(id, updates);
        logger.info("Client patched successfully");
        return ResponseEntity.ok(patchedClient);
    }

    // ============ DELETE CLIENT ============
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteClient(@PathVariable Long id) {
        logger.info("Deleting client with ID: {}", id);
        clientService.deleteClient(id);
        logger.info("Client deleted successfully with ID: {}", id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Client deleted successfully");
        response.put("id", id.toString());
        return ResponseEntity.ok(response);
    }

    // ============ SEARCH CLIENTS ============
    @GetMapping("/search")
    public ResponseEntity<List<Client>> searchClients(@RequestParam String name) {
        logger.info("Searching clients with name containing: {}", name);
        List<Client> clients = clientService.searchClientsByName(name);
        logger.info("Found {} clients matching search criteria", clients.size());
        return ResponseEntity.ok(clients);
    }

    // ============ GET CLIENT COUNT ============
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getClientCount() {
        logger.info("Counting total clients");
        long count = clientService.getClientCount();
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        logger.info("Total clients: {}", count);
        return ResponseEntity.ok(response);
    }
}