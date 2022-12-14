package ru.kir.credit.web.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import ru.kir.credit.web.dtos.CreditDto;
import ru.kir.credit.web.entities.Credit;
import ru.kir.credit.web.error_handling.InvalidDataException;
import ru.kir.credit.web.services.CreditService;
import ru.kir.credit.web.utils.Validator;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/credits")
@RequiredArgsConstructor
public class CreditController {
    private final CreditService creditService;

    @GetMapping
    public List<CreditDto> findAllCredits() {
        return creditService.findAllCredits()
                .stream()
                .map(CreditDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public CreditDto addNewCredit(@RequestBody CreditDto creditDto) {
        boolean checkNumber = Validator.checkNumber(creditDto.getCreditLimit());

        if (!checkNumber) {
            throw new InvalidDataException("Credit limit not specified or credit limit <= 0");
        }

        checkNumber = Validator.checkNumber(creditDto.getInterestRate());

        if (!checkNumber) {
            throw new InvalidDataException("Interest rate not specified or Interest rate <= 0");
        }

        Credit credit = creditService.addNewCredit(creditDto)
                .orElseThrow(() -> new InvalidDataException(String.format("Credit with a credit limit: '%.2f' and an interest rate: '%.2f' already exists",
                        creditDto.getCreditLimit(),
                        creditDto.getInterestRate())));

        return new CreditDto(credit);
    }

    @PutMapping("/{uuid}")
    public CreditDto updateCredit(@PathVariable String uuid,
                                  @RequestBody CreditDto creditDto) {
        boolean checker = Validator.checkString(uuid);

        if (!checker) {
            throw new InvalidDataException("Credit uuid not specified");
        }

        checker = Validator.checkNumber(creditDto.getCreditLimit());

        if (!checker) {
            throw new InvalidDataException("Credit limit not specified or credit limit <= 0");
        }

        checker = Validator.checkNumber(creditDto.getInterestRate());

        if (!checker) {
            throw new InvalidDataException("Interest rate not specified or interest rate <= 0");
        }

        Credit credit = creditService.updateCredit(uuid, creditDto)
                .orElseThrow(() -> new InvalidDataException(String.format("Credit with a credit limit: '%.2f' and an interest rate: '%.2f' already exists",
                        creditDto.getCreditLimit(), creditDto.getInterestRate())));

        return new CreditDto(credit);
    }

    @DeleteMapping("/{uuid}")
    public void deleteCreditByUuid(@PathVariable String uuid) {
        boolean checkStr = Validator.checkString(uuid);

        if (!checkStr) {
            throw new InvalidDataException("Credit uuid not specified");
        }

        creditService.deleteCreditByUuid(uuid);
    }

}
