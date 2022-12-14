package ru.kir.credit.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kir.credit.web.dtos.ClientDto;
import ru.kir.credit.web.entities.Client;
import ru.kir.credit.web.error_handling.InvalidDataException;
import ru.kir.credit.web.services.ClientService;
import ru.kir.credit.web.utils.Validator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @GetMapping
    public List<ClientDto> findAllClients() {
        return clientService.findAllClients()
                .stream()
                .map(ClientDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ClientDto addNewClient(@RequestBody ClientDto clientDto) {
        boolean checkStr = Validator.checkString(clientDto.getFullName());

        if (!checkStr) {
            throw new InvalidDataException("Client full name not specified");
        }

        checkStr = Validator.checkString(clientDto.getPhoneNumber());

        if (!checkStr) {
            throw new InvalidDataException("Client phone number not specified");
        }

        checkStr = Validator.checkString(clientDto.getEmail());

        if (!checkStr) {
            throw new InvalidDataException("Client email not specified");
        }

        checkStr = Validator.checkString(clientDto.getEmail());

        if (!checkStr) {
            throw new InvalidDataException("Client passport number not specified");
        }

        Client client = clientService.addNewClient(clientDto)
                .orElseThrow(() -> new InvalidDataException(String.format("Client with a passport number: '%s' already exists", clientDto.getPassportNumber())));
        return new ClientDto(client);
    }

    @PutMapping("/{uuid}")
    public ClientDto updateClient(@PathVariable String uuid,
                                  @RequestBody ClientDto clientDto) {
        boolean checkStr = Validator.checkString(uuid);

        if (!checkStr) {
            throw new InvalidDataException("Client uuid not specified");
        }

        checkStr = Validator.checkString(clientDto.getFullName());

        if (!checkStr) {
            throw new InvalidDataException("Client full name not specified");
        }

        checkStr = Validator.checkString(clientDto.getPhoneNumber());

        if (!checkStr) {
            throw new InvalidDataException("Client phone number not specified");
        }

        checkStr = Validator.checkString(clientDto.getEmail());

        if (!checkStr) {
            throw new InvalidDataException("Client email not specified");
        }

        checkStr = Validator.checkString(clientDto.getEmail());

        if (!checkStr) {
            throw new InvalidDataException("Client passport number not specified");
        }

        Client client = clientService.updateClient(uuid, clientDto)
                .orElseThrow(() -> new InvalidDataException(String.format("Client with a passport number: '%s' already exists", clientDto.getPassportNumber())));
        return new ClientDto(client);
    }

    @DeleteMapping("/{uuid}")
    public void deleteClientByUuid(@PathVariable String uuid) {
        boolean checkStr = Validator.checkString(uuid);

        if (!checkStr) {
            throw new InvalidDataException("Client uuid not specified");
        }

        clientService.deleteClientByUuid(uuid);
    }

}
