package ru.kir.credit.web.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
@Data
public class CreditOfferFormDto {
    private String clientUuid;
    private String creditUuid;
    private BigDecimal creditSum;

}
