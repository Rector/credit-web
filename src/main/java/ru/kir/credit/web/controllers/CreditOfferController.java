package ru.kir.credit.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.kir.credit.web.dtos.*;
import ru.kir.credit.web.entities.CreditOffer;
import ru.kir.credit.web.error_handling.InvalidDataException;
import ru.kir.credit.web.services.CreditOfferService;
import ru.kir.credit.web.utils.Validator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/credit_offers")
@RequiredArgsConstructor
public class CreditOfferController {
    private final CreditOfferService creditOfferService;

    @GetMapping
    public List<CreditOfferDto> findAllCreditOffers() {
        return creditOfferService.findAllCreditOffers()
                .stream()
                .map(CreditOfferDto::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/{uuid}")
    public CreditOfferDto findCreditOfferByUuid(@PathVariable String uuid) {
        boolean checkStr = Validator.checkString(uuid);

        if (!checkStr) {
            throw new InvalidDataException("Credit offer uuid not specified");
        }

        CreditOffer creditOffer = creditOfferService.checkAndFindCreditOfferByUuid(uuid);
        return new CreditOfferDto(creditOffer);
    }

    @PostMapping
    public CreditOfferDto addNewCreditOffer(@RequestBody CreditOfferFormDto creditOfferFormDto) {
        boolean checker = Validator.checkString(creditOfferFormDto.getClientUuid());

        if (!checker) {
            throw new InvalidDataException("Client uuid not specified");
        }

        checker = Validator.checkString(creditOfferFormDto.getCreditUuid());

        if (!checker) {
            throw new InvalidDataException("Credit uuid not specified");
        }

        checker = Validator.checkNumber(creditOfferFormDto.getCreditSum());

        if (!checker) {
            throw new InvalidDataException("Credit sum not specified or credit sum <= 0");
        }

        CreditOffer creditOffer = creditOfferService.addNewCreditOffer(creditOfferFormDto)
                .orElseThrow(() -> new InvalidDataException("Error when creating credit offer"));

        return new CreditOfferDto(creditOffer);
    }

    @PutMapping("/{uuid}")
    public CreditOfferDto updateCreditOffer(@PathVariable String uuid,
                                            @RequestBody CreditOfferFormDto creditOfferFormDto) {
        boolean checker = Validator.checkString(uuid);

        if (!checker) {
            throw new InvalidDataException("Credit offer uuid not specified");
        }

        checker = Validator.checkString(creditOfferFormDto.getClientUuid());

        if (!checker) {
            throw new InvalidDataException("Client uuid not specified");
        }

        checker = Validator.checkString(creditOfferFormDto.getCreditUuid());

        if (!checker) {
            throw new InvalidDataException("Credit uuid not specified");
        }

        checker = Validator.checkNumber(creditOfferFormDto.getCreditSum());

        if (!checker) {
            throw new InvalidDataException("Credit sum not specified or credit sum <= 0");
        }

        CreditOffer creditOffer = creditOfferService.updateCreditOffer(uuid, creditOfferFormDto)
                .orElseThrow(() -> new InvalidDataException("Error when updating credit offer"));

        return new CreditOfferDto(creditOffer);
    }

    @DeleteMapping("/{uuid}")
    public void deleteCreditOfferByUuid(@PathVariable String uuid) {
        boolean checkerStr = Validator.checkString(uuid);

        if (!checkerStr) {
            throw new InvalidDataException("Credit offer uuid not specified");
        }

        creditOfferService.deleteCreditOfferByUuid(uuid);
    }

}
