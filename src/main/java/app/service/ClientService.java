package app.service;

import app.exception.ClientException;
import app.model.Client;
import app.repository.ClientRepository;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class ClientService {
    @Inject
    private ClientRepository clientRepository;

    public Client createClient(Client client) {
        return clientRepository.save(client);
    }

    public void deleteClient(Long id) {
        if (clientRepository.existsById(id)) {
            clientRepository.deleteById(id);
        }
        throw new ClientException.ClientNotFoundException("Client not found");
    }

    public Client updateClient(Client client) {
        if (clientRepository.existsById(client.getId())) {
            return clientRepository.update(client);
        }
        throw new ClientException.ClientNotFoundException("Client not found");
    }

    public Client getClient(Long id) {
        if (clientRepository.existsById(id)) {
            return clientRepository.findById(id).orElse(null);
        }
        throw new ClientException.ClientNotFoundException("Client not found");
    }

    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }
}