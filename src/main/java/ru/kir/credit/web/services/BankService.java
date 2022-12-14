package ru.kir.credit.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.credit.web.entities.Bank;
import ru.kir.credit.web.entities.Client;
import ru.kir.credit.web.entities.Credit;
import ru.kir.credit.web.error_handling.ResourceNotFoundException;
import ru.kir.credit.web.repositories.BankRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BankService {
    private final BankRepository bankRepository;
    private final ClientService clientService;
    private final CreditService creditService;

    public List<Bank> findAllBanks() {
        return bankRepository.findAll();
    }

    public Optional<Bank> findBankByUuid(String uuid) {
        return bankRepository.findById(uuid);
    }

    @Transactional
    public Optional<Bank> addNewBank() {
        Bank bank = new Bank();
        bankRepository.save(bank);
        return Optional.of(bank);
    }

    public Bank checkAndFindBankByUuid(String uuid) {
        return bankRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Bank with uuid: '%s' not found", uuid)));
    }

    public List<Client> findAllClientsForBank() {
        return clientService.findAllClients();
    }

    public List<Credit> findAllCreditsForBank() {
        return creditService.findAllCredits();
    }

    @Transactional
    public Optional<Client> addClientBank(String bankUuid, String clientUuid) {
        Bank bank = checkAndFindBankByUuid(bankUuid);
        Client client = clientService.checkAndFindClientByUuid(clientUuid);
        List<Client> bankClientList = bank.getClientList();

        if (bankClientList == null) {
            bankClientList = new ArrayList<>(Arrays.asList(client));
            bank.setClientList(bankClientList);
            return Optional.of(client);
        }

        if (!bankClientList.contains(client)) {
            bank.getClientList().add(client);
            return Optional.of(client);
        }

        return Optional.empty();
    }

    @Transactional
    public void deleteClientBank(String bankUuid, String clientUuid) {
        Bank bank = checkAndFindBankByUuid(bankUuid);
        Client client = clientService.checkAndFindClientByUuid(clientUuid);
        List<Client> bankClientList = bank.getClientList();

        if (bankClientList == null || bankClientList.isEmpty()) {
            return;
        }

        if (bankClientList.contains(client)) {
            bank.getClientList().remove(client);
        }
    }

    @Transactional
    public Optional<Credit> addCreditBank(String bankUuid, String creditUuid) {
        Bank bank = checkAndFindBankByUuid(bankUuid);
        Credit credit = creditService.checkAndFindCreditByUuid(creditUuid);
        List<Credit> bankCreditList = bank.getCreditList();

        if (bankCreditList == null) {
            bankCreditList = new ArrayList<>(Arrays.asList(credit));
            bank.setCreditList(bankCreditList);
            return Optional.of(credit);
        }

        if (!bankCreditList.contains(credit)) {
            bank.getCreditList().add(credit);
            return Optional.of(credit);
        }

        return Optional.empty();
    }

    @Transactional
    public void deleteBankCredit(String bankUuid, String creditUuid) {
        Bank bank = checkAndFindBankByUuid(bankUuid);
        Credit credit = creditService.checkAndFindCreditByUuid(creditUuid);
        List<Credit> bankCreditList = bank.getCreditList();

        if (bankCreditList == null || bankCreditList.isEmpty()) {
            return;
        }

        if (bankCreditList.contains(credit)) {
            bank.getCreditList().remove(credit);
        }
    }

    @Transactional
    public void deleteBankByUuid(String uuid) {
        Bank bank = checkAndFindBankByUuid(uuid);
        bankRepository.delete(bank);
    }

}
