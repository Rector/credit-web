package ru.kir.credit.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.credit.web.dtos.ClientDto;
import ru.kir.credit.web.entities.Client;
import ru.kir.credit.web.error_handling.ResourceNotFoundException;
import ru.kir.credit.web.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository clientRepository;

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    @Transactional
    public Optional<Client> addNewClient(ClientDto clientDto) {
        Optional<Client> optionalClient = clientRepository.findByPassportNumber(clientDto.getPassportNumber());

        if (!optionalClient.isPresent()) {
            Client client = new Client();
            client.setFullName(clientDto.getFullName());
            client.setPhoneNumber(clientDto.getPhoneNumber());
            client.setEmail(clientDto.getEmail());
            client.setPassportNumber(clientDto.getPassportNumber());

            clientRepository.save(client);
            return Optional.of(client);
        }

        return Optional.empty();
    }

    public Client checkAndFindClientByUuid(String uuid) {
        return clientRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Client with uuid: '%s' not found", uuid)));
    }

    @Transactional
    public Optional<Client> updateClient(String uuid, ClientDto clientDto) {
        Client client = checkAndFindClientByUuid(uuid);

        Optional<Client> optionalClient = clientRepository.findByPassportNumber(clientDto.getPassportNumber());

        if (!optionalClient.isPresent()) {
            client.setFullName(clientDto.getFullName());
            client.setPhoneNumber(clientDto.getPhoneNumber());
            client.setEmail(clientDto.getEmail());
            client.setPassportNumber(clientDto.getPassportNumber());

            return Optional.of(client);
        }

        return Optional.empty();
    }

    @Transactional
    public void deleteClientByUuid(String uuid) {
        Client client = checkAndFindClientByUuid(uuid);
        clientRepository.delete(client);
    }

}
