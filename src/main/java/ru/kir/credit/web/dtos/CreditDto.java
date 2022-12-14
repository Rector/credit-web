package ru.kir.credit.web.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.kir.credit.web.entities.Credit;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class CreditDto {
    private String uuid;
    private BigDecimal creditLimit;
    private BigDecimal interestRate;

    public CreditDto(Credit credit) {
        this.uuid = credit.getUuid();
        this.creditLimit = credit.getCreditLimit();
        this.interestRate = credit.getInterestRate();
    }

}
