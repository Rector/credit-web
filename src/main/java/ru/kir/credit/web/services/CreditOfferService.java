package ru.kir.credit.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.credit.web.dtos.CreditOfferFormDto;
import ru.kir.credit.web.entities.Client;
import ru.kir.credit.web.entities.Credit;
import ru.kir.credit.web.entities.CreditOffer;
import ru.kir.credit.web.entities.PaymentSchedule;
import ru.kir.credit.web.error_handling.InvalidDataException;
import ru.kir.credit.web.error_handling.ResourceNotFoundException;
import ru.kir.credit.web.repositories.CreditOfferRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CreditOfferService {
    private final CreditOfferRepository creditOfferRepository;
    private final ClientService clientService;
    private final CreditService creditService;
    private final PaymentScheduleService paymentScheduleService;

    public List<CreditOffer> findAllCreditOffers() {
        return creditOfferRepository.findAll();
    }

    @Transactional
    public Optional<CreditOffer> addNewCreditOffer(CreditOfferFormDto creditOfferFormDto) {
        CreditOffer creditOffer = new CreditOffer();

        Client client = clientService.checkAndFindClientByUuid(creditOfferFormDto.getClientUuid());
        creditOffer.setClient(client);

        Credit credit = creditService.checkAndFindCreditByUuid(creditOfferFormDto.getCreditUuid());

        if (creditOfferFormDto.getCreditSum().doubleValue() > credit.getCreditLimit().doubleValue()) {
            throw new InvalidDataException(String.format("Credit limit: '%.2f' exceeded", credit.getCreditLimit().doubleValue()));
        }

        creditOffer.setCredit(credit);

        creditOffer.setCreditSum(creditOfferFormDto.getCreditSum());

        List<PaymentSchedule> paymentScheduleList = paymentScheduleService.addNewPaymentScheduleForCreditOffer(
                creditOfferFormDto.getCreditSum(),
                credit.getInterestRate());
        creditOffer.setPaymentScheduleList(paymentScheduleList);

        creditOfferRepository.save(creditOffer);

        return Optional.of(creditOffer);
    }

    @Transactional
    public Optional<CreditOffer> updateCreditOffer(String uuid, CreditOfferFormDto creditOfferFormDto) {
        CreditOffer creditOffer = checkAndFindCreditOfferByUuid(uuid);

        Client client = clientService.checkAndFindClientByUuid(creditOfferFormDto.getClientUuid());
        creditOffer.setClient(client);

        Credit credit = creditService.checkAndFindCreditByUuid(creditOfferFormDto.getCreditUuid());

        if (creditOfferFormDto.getCreditSum().doubleValue() > credit.getCreditLimit().doubleValue()) {
            throw new InvalidDataException(String.format("Credit limit: '%.2f' exceeded", credit.getCreditLimit().doubleValue()));
        }

        creditOffer.setCredit(credit);
        creditOffer.setCreditSum(creditOfferFormDto.getCreditSum());

        List<PaymentSchedule> paymentScheduleList = paymentScheduleService.updatePaymentScheduleForCreditOffer(
                creditOffer.getPaymentScheduleList(),
                creditOfferFormDto.getCreditSum(),
                credit.getInterestRate());
        creditOffer.setPaymentScheduleList(paymentScheduleList);

        return Optional.of(creditOffer);
    }

    public CreditOffer checkAndFindCreditOfferByUuid(String uuid) {
        return creditOfferRepository.findById(uuid)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("CreditOffer with uuid: '%s' not found", uuid)));
    }

    @Transactional
    public void deleteCreditOfferByUuid(String uuid) {
        CreditOffer creditOffer = checkAndFindCreditOfferByUuid(uuid);
        creditOfferRepository.delete(creditOffer);
    }

}