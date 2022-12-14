package ru.kir.credit.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kir.credit.web.dtos.BankDto;
import ru.kir.credit.web.dtos.ClientDto;
import ru.kir.credit.web.dtos.CreditDto;
import ru.kir.credit.web.entities.Bank;
import ru.kir.credit.web.entities.Client;
import ru.kir.credit.web.entities.Credit;
import ru.kir.credit.web.error_handling.InvalidDataException;
import ru.kir.credit.web.services.BankService;
import ru.kir.credit.web.utils.Validator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/banks")
@RequiredArgsConstructor
public class BankController {
    private final BankService bankService;

    @GetMapping
    public List<BankDto> findAllBanks() {
        return bankService.findAllBanks()
                .stream()
                .map(BankDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("{uuid}/clients")
    public List<ClientDto> findAllClientsForBank(@PathVariable String uuid) {
        boolean checkStr = Validator.checkString(uuid);

        if (!checkStr) {
            throw new InvalidDataException("Bank uuid not specified");
        }

        List<Client> allClientList = bankService.findAllClientsForBank();
        List<Client> bankClientList = bankService.checkAndFindBankByUuid(uuid).getClientList();

        if (bankClientList == null || bankClientList.isEmpty()) {
            return allClientList
                    .stream()
                    .map(ClientDto::new)
                    .collect(Collectors.toList());
        }

        allClientList.removeAll(bankClientList);

        return allClientList
                .stream()
                .map(ClientDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("{uuid}/credits")
    public List<CreditDto> findAllCreditsForBank(@PathVariable String uuid) {
        boolean checkStr = Validator.checkString(uuid);

        if (!checkStr) {
            throw new InvalidDataException("Bank uuid not specified");
        }

        List<Credit> allCreditList = bankService.findAllCreditsForBank();
        List<Credit> bankCreditList = bankService.checkAndFindBankByUuid(uuid).getCreditList();

        if (bankCreditList == null || bankCreditList.isEmpty()) {
            return allCreditList
                    .stream()
                    .map(CreditDto::new)
                    .collect(Collectors.toList());
        }

        allCreditList.removeAll(bankCreditList);

        return allCreditList
                .stream()
                .map(CreditDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{uuid}")
    public BankDto findBankByUuid(@PathVariable String uuid) {
        boolean checkStr = Validator.checkString(uuid);

        if (!checkStr) {
            throw new InvalidDataException("Bank uuid not specified");
        }

        Bank bank = bankService.checkAndFindBankByUuid(uuid);
        return new BankDto(bank);
    }

    @PostMapping
    public BankDto addNewBank() {
        Bank bank = bankService.addNewBank()
                .orElseThrow(() -> new InvalidDataException("Bank creation error"));

        return new BankDto(bank);
    }

    @PostMapping("/{uuid}/client_bank")
    public ClientDto addBankClient(@PathVariable(name = "uuid") String bankUuid, @RequestBody String clientUuid) {
        boolean checkStr = Validator.checkString(bankUuid);

        if (!checkStr) {
            throw new InvalidDataException("Bank uuid not specified");
        }

        checkStr = Validator.checkString(clientUuid);

        if (!checkStr) {
            throw new InvalidDataException("Client uuid not specified");
        }

        Client client = bankService.addClientBank(bankUuid, clientUuid)
                .orElseThrow(() -> new InvalidDataException(String.format("Bank client with a uuid: '%s' already exists", clientUuid)));

        return new ClientDto(client);
    }

    @DeleteMapping("/{uuid}/client_bank/{client_uuid}")
    public void deleteBankClient(@PathVariable(name = "uuid") String bankUuid,
                                 @PathVariable(name = "client_uuid") String clientUuid) {
        boolean checkStr = Validator.checkString(bankUuid);

        if (!checkStr) {
            throw new InvalidDataException("Bank uuid not specified");
        }

        checkStr = Validator.checkString(clientUuid);

        if (!checkStr) {
            throw new InvalidDataException("Client uuid not specified");
        }

        bankService.deleteClientBank(bankUuid, clientUuid);
    }

    @PostMapping("/{uuid}/credit_bank")
    public CreditDto addBankCredit(@PathVariable(name = "uuid") String bankUuid, @RequestBody String creditUuid) {
        boolean checkStr = Validator.checkString(bankUuid);

        if (!checkStr) {
            throw new InvalidDataException("Bank uuid not specified");
        }

        checkStr = Validator.checkString(creditUuid);

        if (!checkStr) {
            throw new InvalidDataException("Credit uuid not specified");
        }

        Credit credit = bankService.addCreditBank(bankUuid, creditUuid)
                .orElseThrow(() -> new InvalidDataException(String.format("Bank credit with a uuid: '%s' already exists", creditUuid)));
        return new CreditDto(credit);
    }

    @DeleteMapping("/{uuid}/credit_bank/{credit_uuid}")
    public void deleteBankCredit(@PathVariable(name = "uuid") String bankUuid,
                                 @PathVariable(name = "credit_uuid") String creditUuid) {
        boolean checkStr = Validator.checkString(bankUuid);

        if (!checkStr) {
            throw new InvalidDataException("Bank uuid not specified");
        }

        checkStr = Validator.checkString(creditUuid);

        if (!checkStr) {
            throw new InvalidDataException("Credit uuid not specified");
        }

        bankService.deleteBankCredit(bankUuid, creditUuid);
    }

    @DeleteMapping("/{uuid}")
    public void deleteBankByUuid(@PathVariable String uuid) {
        boolean checkStr = Validator.checkString(uuid);

        if (!checkStr) {
            throw new InvalidDataException("Bank uuid not specified");
        }

        bankService.deleteBankByUuid(uuid);
    }

}
