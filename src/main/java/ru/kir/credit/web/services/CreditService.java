package ru.kir.credit.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.credit.web.dtos.CreditDto;
import ru.kir.credit.web.entities.Credit;
import ru.kir.credit.web.error_handling.ResourceNotFoundException;
import ru.kir.credit.web.repositories.CreditRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CreditService {
    private final CreditRepository creditRepository;

    public List<Credit> findAllCredits() {
        return creditRepository.findAll();
    }

    @Transactional
    public Optional<Credit> addNewCredit(CreditDto creditDto) {
        Optional<Credit> optionalClient = creditRepository.findByCreditLimitAndInterestRate(
                creditDto.getCreditLimit(),
                creditDto.getInterestRate());

        if (!optionalClient.isPresent()) {
            Credit credit = new Credit();
            credit.setCreditLimit(creditDto.getCreditLimit());
            credit.setInterestRate(creditDto.getInterestRate());

            creditRepository.save(credit);
            return Optional.of(credit);
        }

        return Optional.empty();
    }

    public Credit checkAndFindCreditByUuid(String uuid) {
        return creditRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Credit with uuid: '%s' not found", uuid)));
    }

    @Transactional
    public Optional<Credit> updateCredit(String uuid, CreditDto creditDto) {
        Credit credit = checkAndFindCreditByUuid(uuid);

        Optional<Credit> optionalCredit = creditRepository.findByCreditLimitAndInterestRate(creditDto.getCreditLimit(), creditDto.getInterestRate());

        if (!optionalCredit.isPresent()) {
            credit.setCreditLimit(creditDto.getCreditLimit());
            credit.setInterestRate(creditDto.getInterestRate());

            return Optional.of(credit);
        }

        return Optional.empty();
    }

    @Transactional
    public void deleteCreditByUuid(String uuid) {
        Credit credit = checkAndFindCreditByUuid(uuid);
        creditRepository.delete(credit);
    }

}
